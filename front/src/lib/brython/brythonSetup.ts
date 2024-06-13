export async function runPythonCode(editor: any, stage: string, capturedPrints: string[]) {
    // @ts-ignore
    const runner = new BrythonRunner({
        stderr: {
            write(content: string) {
                console.error('StdErr: ' + content);
            },
            flush() {},
        },
        stdout: {
            write(content: string) {
                capturedPrints.push(content);  
            },
            flush() {},
        },
    });
    const stageCode = stage.split('\n').map((line: String) => `    ${line}`).join('\n');
    const userCodeIndented = editor.getValue().split('\n').map((line: String) => `    ${line}`).join('\n');
    const finalCode = `${gamePre}\n${stageCode}\n${initialCode}\n${userCodeIndented}\n${gamePost}`; // TODO REMOVE THIS
    await runner.runCode(finalCode);
    }

    var gamePre = 
    `
    import copy
    import inspect
    import json
    
    class Character:
        def __init__(self, data):
            # 세팅값
            self.fps = 30
            self.go_time = 2.0
            self.go_cannot_time = 0.5
            self.turn_time = 0.3
            self.hit_bomb_time = 0.5
            self.set_time= 0.5
            self.set_cannot_time= 0.5
            self.wait_time = 0.5
    
            self.hero_die_status = 9
            self.hero_cannot_move_status = 10
            self.hero_hit_bomb_status = 11
            self.hero_do_set_status = 12
            self.hero_miss_dir_status = 13
            self.hero_miss_item_status = 14
            
            # 초기값
            self.data = data
            
            # 결과값
            self.frames = []
    
        def frame_append(self, line_num):
            self.frames.append({
                "id": len(self.frames), 
                "status": 0, 
                "line_num": line_num,
                "player": copy.deepcopy(self.data["player"]), 
                "item_list": copy.deepcopy(self.data["item_list"])
                })
    
        def set_fail(self, status, line_num):
            total_frames = int(self.set_cannot_time * self.fps)
            for _ in range(total_frames):
                self.frames.append({
                    "id": len(self.frames), 
                    "status": 0, 
                    "line_num": line_num - start_line, 
                    "player": {**copy.deepcopy(self.data["player"]), "status": status}, 
                    "item_list": copy.deepcopy(self.data["item_list"])
                    })
                
        def set_success(self, line_num):
            total_frames = int(self.set_time * self.fps)
            for _ in range(total_frames):
                self.frames.append({
                    "id": len(self.frames), 
                    "status": 0, 
                    "line_num": line_num - start_line, 
                    "player": {**copy.deepcopy(self.data["player"]), "status": self.hero_do_set_status}, 
                    "item_list": copy.deepcopy(self.data["item_list"])
                    })
    
        def can_move(self):
            now_x = int(round(self.data["player"]["pos"][0]))
            now_y = int(round(self.data["player"]["pos"][1]))
            new_x = now_x
            new_y = now_y
            new_x_middle = now_x
            new_y_middle = now_y
            
            # 이동할 좌표 계산
            # 기본 이동 2칸씩 [new_x, new_y]
            # 중간지점 [new_x_middle, new_y_middle]
            if self.data["player"]["dir"] == "right":
                new_x = round(now_x + 2)
                new_x_middle = round(now_x + 1)
            elif self.data["player"]["dir"] == "left":
                new_x = round(now_x - 2)
                new_x_middle = round(now_x - 1)
            elif self.data["player"]["dir"] == "up":
                new_y = round(now_y - 2)
                new_y_middle = round(now_y - 1)
            elif self.data["player"]["dir"] == "down":
                new_y = round(now_y + 2)
                new_y_middle = round(now_y + 1)
            
            # 맵 밖으로 나가는지 체크
            if new_x < 0 or new_y < 0 or new_y >= len(self.data["stage"]["tile"]) or new_x >= len(self.data["stage"]["tile"][0]):
                return 1
    
            # item_list 로 중간위치 체크
            for item in self.data['item_list']:
                if item['type'] == 'bomb' and item['status'] == 1 and [new_x_middle, new_y_middle] == item['pos']: # 폭탄 밟음
                    return 2
                
                elif item['type'] == 'laser' and item['status'] == 1: # 레이저 닿음
                    player_middle_pos_rounded = [new_x_middle, new_y_middle]
                    
                    if (item['dir'] == 'h' and player_middle_pos_rounded[1] == item["pos_start"][1] and # 가로레이저
                        item["pos_start"][0] <= player_middle_pos_rounded[0] <= item["pos_end"][0]):
                            return 3
    
                    elif (item['dir'] == 'v' and player_middle_pos_rounded[0] == item["pos_start"][0] and # 세로 레이저
                        item["pos_start"][1] <= player_middle_pos_rounded[1] <= item["pos_end"][1]):
                            return 3
            # 중간위치 벽 체크 
            if self.data["stage"]["tile"][new_y_middle][new_x_middle] == 2:
                return 1
            
            return 0
        
        def decision_dir(self, directions): # 회전 후 방향 결정
            dir_index = directions.index(self.data["player"]["dir"]) 
            new_dir_index = (dir_index + 1) % len(directions)
    
            return directions[new_dir_index]
        
        def do_move(self, total_frames, line_num):
            if self.data["player"]["dir"] == "right":
                for _ in range(int(total_frames)):
                    self.move(2/int(self.go_time * self.fps), 0, line_num)
            elif self.data["player"]["dir"] == "left":
                for _ in range(int(total_frames)):
                    self.move(-2/int(self.go_time * self.fps), 0, line_num)
            elif self.data["player"]["dir"] == "up":
                for _ in range(int(total_frames)):
                    self.move(0, -2/int(self.go_time * self.fps), line_num)
            elif self.data["player"]["dir"] == "down":
                for _ in range(int(total_frames)):
                    self.move(0, 2/int(self.go_time * self.fps), line_num)        
    
        def move(self, dx, dy, line_num):
            new_x = self.data["player"]["pos"][0] + dx
            new_y = self.data["player"]["pos"][1] + dy
            
            # 플레이어 이동
            self.data["player"]["pos"] = [new_x, new_y]
            # 결과값 저장
            self.frame_append(line_num - start_line)
    
        def turn_half(self, line_num): 
            total_frames = int(self.turn_time * self.fps)
    
            for _ in range(int(total_frames/2)):
                self.frame_append(line_num - start_line)
                
        def go(self, line_num):
            if self.can_move() == 0:
                # 총프레임수 = 이동시간(초) * 초당프레임수
                total_frames = int(self.go_time * self.fps)
                self.do_move(total_frames, line_num)
            elif self.can_move() == 1:
                total_frames = int(self.go_cannot_time * self.fps)
                # 결과값 저장  10:이동불가 말풍선
                for _ in range(total_frames):
                    self.frames.append({
                        "id": len(self.frames), 
                        "status":0, 
                        "line_num":line_num - start_line, 
                        "player": {**copy.deepcopy(self.data["player"]), 
                        "status": self.hero_cannot_move_status}, 
                        "item_list": copy.deepcopy(self.data["item_list"])
                        })
            elif self.can_move() == 2: # 중간에 폭탄밟음
                move_frames = int(self.go_time * self.fps)
                action_frames = int(self.hit_bomb_time * self.fps)
                self.do_move(move_frames/2, line_num)
                for item in self.data['item_list']:
                    if item['type'] == 'bomb' and [round(pos) for pos in self.data["player"]["pos"]] == item['pos']:
                        item["status"] = 0
                self.data["player"]["hp"] -= 50
                if self.data["player"]["hp"] <= 0: # 플레이어 사망
                    return
    
                for _ in range(action_frames):
                    self.frames.append({
                        "id": len(self.frames), 
                        "status":0, 
                        "line_num":line_num - start_line, 
                        "player": {**copy.deepcopy(self.data["player"]), "status": self.hero_hit_bomb_status}, 
                        "item_list": copy.deepcopy(self.data["item_list"])
                        })
                self.do_move(move_frames/2, line_num)
    
            elif self.can_move() == 3: # 중간에 레이져 밟음 ToDo: 나중에 디버깅 및 수정 필요
                move_frames = int(self.go_time * self.fps)
                self.do_move(move_frames/2, line_num)
                self.data["player"]["hp"] = 0
                return
    
    
    
        def turnRight(self, line_num): 
            self.turn_half(line_num)
    
            directions = ["right", "down", "left", "up"] 
            self.data["player"]["dir"] = self.decision_dir(directions)
    
            self.turn_half(line_num)
    
        def turnLeft(self, line_num):
            self.turn_half(line_num)
    
            directions = ["right", "up", "left", "down"] 
            self.data["player"]["dir"] = self.decision_dir(directions)
    
            self.turn_half(line_num)
    
        def set(self, item, line_num):
            dir_offsets = {
                'up': (0, -2),
                'down': (0, 2),
                'left': (-2, 0),
                'right': (2, 0)
            }
    
            item_types = ['solid_propellant', 'liquid_fuel', 'engines']
            player_dir = self.data["player"]["dir"]
            player_pos = [round(pos) for pos in self.data["player"]["pos"]]
    
            offset = dir_offsets.get(player_dir, (0, 0))
            target_pos = (player_pos[0] + offset[0], player_pos[1] + offset[1])
    
            for item_data in self.data["item_list"]:
                if (item_data["type"] in item_types and
                    tuple(item_data["pos"]) == target_pos and
                    item_data["status"] == 0):
    
                    if item_data["require_dir"] == player_dir:
    
                        if item_data["name"] == item:
                            item_data["status"] = 1
                            self.set_success(line_num)
                            return
                        else: 
                            self.set_fail(self.hero_miss_item_status, line_num)
                            return
                    else:
                        self.set_fail(self.hero_miss_dir_status, line_num)
                        return
    
            self.set_fail(self.hero_miss_dir_status, line_num)
            
    
        def check_goal(self, line_num): # 목적지 상태 (상호작용) 체크
            if self.data["player"]["hp"] <= 0: # 플레이어 사망
                self.data["player"]["status"] = self.hero_die_status
                self.frame_append(0)
            
            player_pos = [round(pos) for pos in self.data["player"]["pos"]]
                
            for item in self.data['item_list']: # 아이템리스트 상호작용 시작
                if item['type'] == 'food' and player_pos == item['pos']: # food 획득
                    self.data["player"]["food_count"] += 1
                    self.data['item_list'].remove(item)
                    self.frame_append(line_num - start_line)
    
                elif item['type'] == 'rocket_parts' and player_pos == item['pos']: # 로켓부품 획득
                    self.data["player"]["rocket_parts_count"] += 1
                    self.data['item_list'].remove(item)
                    self.frame_append(line_num - start_line)
    
                elif item['type'] == 'laser_switch' and player_pos == item['pos'] and self.frames[-2]["player"]["pos"] != self.data["player"]["pos"]:
                    for laser_id in item["laser_id"]:  # 스위치 작동
                        for laser in self.data['item_list']:
                            if laser['id'] == laser_id:
                                laser["status"] = 1 if laser["status"] == 0 else 0
                                self.frame_append(line_num - start_line)
                                
                elif item['type'] == 'laser' and item['status'] == 1: # 레이저 닿음
                    player_pos_rounded = round(self.data["player"]["pos"][0]), round(self.data["player"]["pos"][1])
                    
                    if (item['dir'] == 'h' and player_pos_rounded[1] == item["pos_start"][1] and # 가로레이저
                        item["pos_start"][0] <= player_pos_rounded[0] <= item["pos_end"][0]):
                            self.data["player"]["hp"] = 0
                            self.data["player"]["status"] = self.hero_die_status
                            self.frame_append(0)
    
                    elif (item['dir'] == 'v' and player_pos_rounded[0] == item["pos_start"][0] and # 세로 레이저
                        item["pos_start"][1] <= player_pos_rounded[1] <= item["pos_end"][1]):
                            self.data["player"]["hp"] = 0
                            self.data["player"]["status"] = self.hero_die_status
                            self.frame_append(0)    
                              
                elif item['type'] == 'drop_switch' and item['count'] > 0 and player_pos == item['pos'] and self.frames[-2]["player"]["pos"] != self.data["player"]["pos"]:
                        item['count'] -= 1
                        if self.data['item_list']:
                            new_id = self.data['item_list'][-1]['id'] + 1
                        else:
                            new_id = 0 
                        new_item = {
                            "id": new_id,
                            "type": item['drop_type'], 
                            "pos": item['pos_drop'] 
                        }
                        self.data['item_list'].append(new_item)
                        self.frame_append(line_num - start_line)
    
            wating_frame = int(self.wait_time * self.fps)
            for _ in range(wating_frame):
                self.frame_append(line_num - start_line)
    
            # 아이템리스트 상호작용 끝
                                   
            # 게임 클리어 체크
            all_achieved = True
            for goal in self.data["stage"]["goal_list"]:
                if not self.check_goal_achieved(goal, line_num):
                    all_achieved = False
                    break  
    
            if all_achieved:
                self.frames.append({
                    "id": len(self.frames),
                    "status": 1,
                    "line_num": 0,
                    "player": copy.deepcopy(self.data["player"]),
                    "item_list": copy.deepcopy(self.data["item_list"])
                })
                
        def check_goal_achieved(self, goal, line_num): # 게임 목표 체크 함수
            if goal['goal'] == 'target':
                return goal["pos"] == [round(pos) for pos in self.data["player"]["pos"]]
            elif goal['goal'] == 'item' and goal['type'] == 'food':
                return goal["count"] <= self.data["player"]["food_count"]
            elif goal['goal'] == 'item' and goal['type'] == 'rocket_parts':
                return goal["count"] <= self.data["player"]["rocket_parts_count"]
            elif goal['goal'] == 'line':
                return goal["count"] >= line_num - start_line
            elif goal['goal'] == 'set':
                count = sum(1 for item in self.data["item_list"] if item["type"] == goal['type'] and item["status"] == 1)
                return count >= goal['count']
    
            return False 
        
        def check_game_status(self): # 게임 종료 체크
            if self.frames:
                if self.frames[-1]["player"]["status"] == self.hero_die_status or self.frames[-1]["status"] == 1:
                    return True
    
        def get_frames(self):
            return self.frames
    
    def go(num = 1):
        for _ in range(num):
            print("go")
            if(hero.check_game_status()):
                return
            hero.go(inspect.currentframe().f_back.f_lineno)
            hero.check_goal(inspect.currentframe().f_back.f_lineno)
    
    def turnRight(num = 1):
        for _ in range(num):
            print("turnRight")
            if(hero.check_game_status()):
                return
            hero.turnRight(inspect.currentframe().f_back.f_lineno)
            hero.check_goal(inspect.currentframe().f_back.f_lineno)
        
    def turnLeft(num = 1):
        for _ in range(num):
            print("turnLeft")
            if(hero.check_game_status()):
                return
            hero.turnLeft(inspect.currentframe().f_back.f_lineno)
            hero.check_goal(inspect.currentframe().f_back.f_lineno)
    
    def set(item):
        print("set("+item+")")
        if(hero.check_game_status()):
            return
        hero.set(item, inspect.currentframe().f_back.f_lineno)
        hero.check_goal(inspect.currentframe().f_back.f_lineno)

    `;

    var initialCode =
    `
    hero = Character(stage)

    start_line = inspect.currentframe().f_lineno
    `;
        
    var gamePost = 
    `
    frames_json = json.dumps(hero.get_frames())
    print(frames_json)
    `;
