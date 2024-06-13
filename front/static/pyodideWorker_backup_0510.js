const logic1 = 
`
import inspect
import json
import sys
import random
from collections import Counter

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
        self.wait_time = 0.2
        self.die_time = 0.5
        self.print_time = 0.5
        self.print_fail_time = 0.5
        self.get_info_time = 0.5
        self.jump_time = 2.0
        self.attack_time = 0.5
        self.check_time = 0.5

        # 상태값
        self.hero_idle_status = 0
        self.hero_run_status = 1
        self.hero_turn_status = 2
        self.hero_get_item_status = 3
        self.hero_die_status = 9
        self.hero_cannot_move_status = 10
        self.hero_hit_bomb_status = 11

        self.hero_miss_dir_status = 13
        self.hero_miss_item_status = 14

        self.hero_set_solid_propellant_status = 15
        self.hero_set_liquid_fuel_status = 16
        self.hero_set_engines_status = 17

        self.hero_miss_args_status = 18
        self.hero_print_status = 19
        # self.hero_get_info_status = 20 # get_info => 100 + item_list index

        self.hero_jump_status = 21
        self.hero_cannot_jump_status = 22  

        self.hero_on_hit_tile_status = 23

        self.hero_heal_status = 24
        self.hero_not_enough_medicine_status = 25

        self.hero_check_status = 26
        self.hero_check_empty_status = 27
        self.hero_check_fail_status = 28

        self.hero_miss_type_status = 29

        self.hero_set_bomb_status = 30
        self.hero_not_enough_bomb_status = 31

        self.hero_cannot_move_monster_status = 32
        self.hero_attack_status = 33

        self.hero_not_enough_energy_status = 34
        self.hero_fail_resolved_status = 35
        self.hero_enough_energy_status = 36
        self.hero_get_energy_status = 37
        self.hero_shoot_status = 38

        self.hero_cannot_jump_gravity_status = 39

        # 아이템 상태값
        self.item_off_status = 0
        self.item_on_status = 1

        self.boss_bomb_damage = 50

        # 몬스터용 상태값
        self.monster_idle_status = -1
        self.monster_error_status = -2
        self.monster_print_status = -3
        self.monster_hit_status = -4 
        self.monster_run_status = -5
        self.monster_attack_status = -6
        self.monster_turn_status = -7
        self.monster_die_status = -8
        self.monster_wait_status = -9
        self.monster_invisible_status = -10
        self.monster_attack_close_status = -11

        self.batch_aggressive_monster_1_action = False

        self.time_count = 0

        # 초기값s
        self.data = data
        self.line_counter = 0
        self.hero_print_array = []
        self.hero_item_list = []
        self.info_print_index = self.update_print_index()

        self.hero_cannot_jump_stage = ['2-2Normal1','2-2Hard1',
                                       '2-3Easy1','2-3Easy2','2-3Normal1','2-3Normal2','2-3Hard1','2-3Hard2',
                                       '3-4Easy1','3-4Easy2','3-4Easy3','3-4Normal1','3-4Normal2','3-4Normal3','3-4Hard1','3-4Hard2','3-4Hard3']
        
        # 결과값
        self.frames = []

        self.make_monster_init_frame()
        self.monster_game_set()

    def monster_game_set(self):
        if self.data['stage']['step'] == '3-4' and self.data['stage']['diff'] == 'Hard' and self.data['stage']['level'] == 2:
            array = [0,1,2,3,4,5,6,7]
            num_elements = random.randint(5, 8)
            selected_elements = random.sample(array, num_elements)

            for item in self.data['stage']['init_item_list']:
                if item['id'] in selected_elements:
                    item['status'] = self.monster_idle_status
                    item['hp'] = random.randint(item['hp'][0], item['hp'][1])
                else:
                    item['status'] = self.monster_die_status
                    item['hp'] = 0  
            return

        count = 0
        for item in self.data['stage']['init_item_list']:
            if item['type'] == 'aggressive_monster_1' and 'variable_no' in item:
                if self.info_print_index in item['variable_no']:
                    count += 1
                    item['status'] = self.monster_wait_status
                else:
                    item['status'] = self.monster_die_status
            if item['type'] == 'aggressive_monster_2' and isinstance(item['hp'], list):
                item['hp'] = random.randint(item['hp'][0], item['hp'][1])

        if 'bomb_count' in self.data['player']:
            if count != 0:
                self.data['player']['bomb_count'] = count

    def update_print_index(self):
        for item in self.data['stage']['init_item_list']:
            if item['type'] == 'print_point':
                if len(item['require_print']) >= 2:
                    return random.randint(0, len(item['require_print']) - 1)
            elif item['type'] == 'box':
                if len(item['item']) >= 2:
                    return random.randint(0, len(item['item']) - 1)
            elif item['type'] == 'monster_info':
                if len(item['item']) >= 2:
                    return random.randint(0, len(item['item']) - 1)
        return 0
    
    def is_boss_stage(self):
        return self.data['stage']['step'] == '3-4' and self.data['stage']['level'] == 3

    def frame_append(self, line_num):
        # self.time_count += 1
        self.batch_aggressive_monster_1()
        if self.batch_aggressive_monster_2() == 9999:
            return
        if self.is_boss_stage():
            self.batch_boss()
    
        self.frames.append({
            "id": len(self.frames), 
            "status": 0, 
            "line_num": line_num,
            "player": self.make_player(), 
            "item_list": self.make_item_list()
            })

    def make_player(self):
        return {
            "pos": [self.data["player"]["pos"][0], self.data["player"]["pos"][1]],
            "dir": self.data["player"]["dir"], 
            "hp": self.data["player"]["hp"],
            "status": self.data["player"]["status"],
            "food_count": self.data["player"]["food_count"],
            "rocket_parts_count": self.data["player"]["rocket_parts_count"],
            "medicine_count": self.data["player"].get("medicine_count", 0),  # stage 데이터에 medicine_count 없으면 0
            "advanced_medicine_count": self.data["player"].get("advanced_medicine_count", 0),  # stage 데이터에 advanced_medicine_count 없으면 0
            "hit_status": self.data["player"].get("hit_status", 0),  # stage 데이터에 hit_status 없으면 0
            "bomb_count": self.data["player"].get("bomb_count", 0),  # stage 데이터에 bomb_count 없으면 0
            "player_check_array": self.data["player"].get("player_check_array", [])  # stage 데이터에 player_check_array 없으면 "
        }

    def make_item_list(self):
        item_list = [] 
        for item in self.data['stage']["init_item_list"]:
            if item['type'] == 'tile_damage':
                continue
            elif item['type'] == 'passive_monster':
                if item['status'] == self.monster_print_status or item['status'] == self.monster_error_status:
                    array = []
                    for i in range(len(self.hero_print_array)):
                        array.append(self.hero_print_array[i])
                    item_list.append({"status": item["status"], "print_array": array})
                    continue
                else: 
                    item_list.append(item["status"])
                    continue
            elif item['type'] == 'aggressive_monster_1':
                if item['status'] == self.monster_die_status or item['status'] == self.monster_invisible_status or item['status'] == self.monster_wait_status:
                    item_list.append(item["status"])
                    continue
                item_list.append({"dir": item["dir"], "pos": [item['pos'][0], item['pos'][1]], "status": item["status"]})
                continue
            elif item['type'] == 'aggressive_monster_2':
                if item['status'] == self.monster_die_status or item['status'] == self.monster_invisible_status or item['status'] == self.monster_wait_status:
                    item_list.append(item["status"])
                    continue
                item_list.append({"pos": [item['pos'][0], item['pos'][1]], "status": item["status"], "hp": item['hp']})
                continue
            elif item['type'] == 'print_point':
                if item['status'] == self.monster_print_status or item['status'] == self.monster_error_status:
                    array = []
                    for i in range(len(self.hero_print_array)):
                        array.append(self.hero_print_array[i])
                    item_list.append({"status": item["status"], "print_array": array})
                    continue
                else:
                    item_list.append({"status": item["status"], "print_array": []})
                    continue
            item_list.append(item["status"])
        return item_list

    def make_monster_init_frame(self):
        monster_types = ['passive_monster', 'aggressive_monster_1', 'aggressive_monster_2', 'boss']

        if any(item['type'] in monster_types for item in self.data['stage']['init_item_list']):
            self.frames.append({
                "id": len(self.frames), 
                "status": 0, 
                "line_num": 0,
                "player": self.make_player(), 
                "item_list": self.make_init_monster_list()
            })

        return
    
    def make_init_monster_list(self):
        item_list = []
        for item in self.data['stage']["init_item_list"]:
            if item['type'] == 'tile_damage':
                continue
            elif item['type'] == 'passive_monster' or item['type'] == 'aggressive_monster_1' or item['type'] == 'aggressive_monster_2' or item['type'] == 'boss':
                item_list.append(self.monster_invisible_status)
            else:
                item_list.append(item["status"])
        
        return item_list
            
    def handle_item_status(self, target_id, status): # 아이템 리스트 두 곳 에서 아이템 status 변경
        for item in self.data['stage']['init_item_list']:
            if item['id'] == target_id:
                item['status'] = status 
                # self.data['item_list'][target_id] = status

        # for item in self.data['item_list']:
        #     if item['id'] == target_id:
        #         item['status'] = status

    def handle_laser_status(self, target_ids): # 레이저 상태 변경 # new version
        for item in self.data['stage']['init_item_list']:
            if item['type'] == 'laser': # new version
                if item['id'] in target_ids: # new version
                    item["status"] = 1 if item["status"] == 0 else 0 # new version
                else:
                    item["status"] = 1 # new version

                # self.data['item_list'][target_id] = 1 if self.data['item_list'][target_id] == 0 else 0     
                
        # for item in self.data['item_list']:
        #     if item['id'] == target_id:
        #         item["status"] = 1 if item["status"] == 0 else 0

    def handle_switch_status(self): # 스위치 상태 변경 (밟혀있던거 다 올라오도록)
        # target_drop_switch_id = []
        # target_laser_switch_id = []

        for item in self.data['stage']['init_item_list']:
            if item['type'] == 'drop_switch':
                if item['status'] == 0:
                    item['status'] = 2
                elif item['status'] == 1:
                    item['status'] = 3
                # target_drop_switch_id.append(item['id'])
            elif item['type'] == 'laser_switch':
                item['status'] = 1
                # target_laser_switch_id.append(item['id'])

        # for index in range(len(self.data['item_list'])):
        #     if index in target_laser_switch_id:
        #         self.data['item_list'][index] = 1
        #     elif index in target_drop_switch_id:
        #         self.data['item_list'][index] = 2 if self.data['item_list'][index] == 0 else 3
        
    def hit_frame_append(self, line_num):
        action_frames = int(self.hit_bomb_time * self.fps)

        self.data["player"]["status"] = self.hero_hit_bomb_status
        for i in range(action_frames):
            self.frame_append(line_num)
        self.data["player"]["status"] = self.hero_idle_status

    def die_frame_append(self):
        action_frames = int(self.die_time * self.fps)

        self.data["player"]["status"] = self.hero_die_status
        for i in range(action_frames):
            self.frame_append(0)

    def set_fail(self, status, line_num):
        total_frames = int(self.set_cannot_time * self.fps)
        self.data["player"]["status"] = status
        for _ in range(total_frames):
            self.frame_append(0)
        self.data["player"]["status"] = 0
            
    def set_success(self, line_num, item):
        items = {"고체추진제": self.hero_set_solid_propellant_status, "액체연료": self.hero_set_liquid_fuel_status, "추가엔진": self.hero_set_engines_status}
        total_frames = int(self.set_time * self.fps)
        self.data["player"]["status"] = items[item]
        for _ in range(total_frames):
            self.frame_append(0)
        self.data["player"]["status"] = 0

    def print_fail(self, status, target_id, line_num):
        total_frames = int(self.print_fail_time * self.fps)
        # self.data["player"]["status"] = status

        # if status == self.hero_miss_args_status:
        self.handle_item_status(target_id, self.monster_error_status)


        for _ in range(total_frames):
            self.frame_append(line_num - start_line)
            
        self.data["player"]["status"] = 0
        self.handle_item_status(target_id, self.monster_idle_status)

    def print_success(self, target_id, target_status, line_num):
        total_frames = int(self.print_time * self.fps)
        self.data["player"]["status"] = self.hero_print_status
        self.handle_item_status(target_id, target_status)

        for _ in range(total_frames):
            self.frame_append(line_num - start_line)
            
        self.data["player"]["status"] = 0
        self.handle_item_status(target_id, self.monster_idle_status)

    def batch_aggressive_monster_1(self):
        for item in self.data['stage']['init_item_list']:
            if item['type'] == 'aggressive_monster_1' and item['status'] != self.monster_die_status:
                # if self.time_count >= item['visible_time']:
                if self.batch_aggressive_monster_1_action:
                    if item['status'] == item['status'] == self.monster_wait_status:
                        item['status'] = self.monster_run_status

                    if item['status'] == self.monster_hit_status:
                        if item['frame_count'] > int(self.hit_bomb_time * self.fps):
                            item['status'] = self.monster_die_status 
                            item['frame_count'] = 0
                            continue
                        else:
                            item['frame_count'] += 1
                            continue
                        
                if item['status'] != self.monster_invisible_status and item['status'] != self.monster_wait_status:
                    monster_pos = [round(pos, 1) for pos in item['pos']]

                    for item_list in self.data['stage']['init_item_list']:
                        if item_list['type'] == 'bomb' and item_list['status'] == self.item_on_status and monster_pos == item_list['pos']:
                            item['status'] = self.monster_hit_status
                            item['frame_count'] += 1
                            item_list['status'] = self.item_off_status
                            continue
                        elif item_list['type'] == 'laser' and item_list['status'] == self.item_on_status: # 레이저 닿음
                            if (item_list['dir'] == 'h' and monster_pos[1] == item_list["pos_start"][1] and # 가로레이저
                                item_list["pos_start"][0] <= monster_pos[0] <= item_list["pos_end"][0]):
                                    item['status'] = self.monster_hit_status
                                    item['frame_count'] += 1
                                    continue

                            elif (item_list['dir'] == 'v' and monster_pos[0] == item_list["pos_start"][0] and # 세로 레이저
                                item_list["pos_start"][1] <= monster_pos[1] <= item_list["pos_end"][1]):
                                    item['status'] = self.monster_hit_status
                                    item['frame_count'] += 1
                                    continue
                    
                    if [round(pos) for pos in self.data["player"]["pos"]] == [monster_pos[0] - 0.9, monster_pos[1]] and item['type'] == 'aggressive_monster_1':
                        if self.data['player']['hp'] == 0 and item['frame_count'] > int(self.hit_bomb_time * self.fps):
                            self.data['player']['status'] = self.hero_die_status
                            item['frame_count'] = 0
                            continue
                        else:
                            item['status'] = self.monster_attack_close_status
                            self.data["player"]["hp"] = 0
                            self.data['player']['status'] = self.hero_hit_bomb_status
                            item['frame_count'] += 1
                            continue
                            
                    if item['goal'] == monster_pos:
                        if self.data['player']['hp'] == 0 and item['frame_count'] > int(self.hit_bomb_time * self.fps):
                            self.data['player']['status'] = self.hero_die_status
                            item['frame_count'] = 0
                            continue
                        else:
                            item['status'] = self.monster_attack_status
                            self.data["player"]["hp"] = 0
                            self.data['player']['status'] = self.hero_hit_bomb_status
                            item['frame_count'] += 1
                            continue
                    else:
                        if item['dir'] == 'right':
                            item['pos'][0] += 2/int(self.go_time * self.fps)
                        elif item['dir'] == 'left':
                            item['pos'][0] -= 2/int(self.go_time * self.fps)


    def batch_aggressive_monster_2(self):
        for item in self.data['stage']['init_item_list']:
            if item['type'] == 'aggressive_monster_2' and item['status'] != self.monster_die_status:
                if item['frame_count'] == 9999:
                    self.data['player']['status'] = self.hero_die_status
                    return 9999
                
                if item['status'] == self.monster_hit_status:
                    if item['hit_frame'] <= int(self.hit_bomb_time * self.fps):
                        item['hit_frame'] += 1
                        continue
                    else:
                        if item['hp'] <= 0:
                            item['status'] = self.monster_die_status
                            item['hit_frame'] = 0
                            continue
                        else:
                            item['status'] = self.monster_idle_status
                            item['hit_frame'] = 0

                #         if item['frame_count'] > int(self.hit_bomb_time * self.fps):
                #             item['status'] = self.monster_die_status 
                #             item['frame_count'] = 0
                #             continue
                #         else:
                #             item['frame_count'] += 1
                #             continue


                if item['status'] != self.monster_invisible_status and item['status'] != self.monster_wait_status:
                    monster_pos = [round(pos, 1) for pos in item['pos']]
                    player_pos = [round(pos) for pos in self.data["player"]["pos"]]

                    # if player_pos == [monster_pos[0] - 2, monster_pos[1]] and item['type'] == 'aggressive_monster_1':
                    #     if self.data['player']['hp'] == 0 and item['frame_count'] > int(self.hit_bomb_time * self.fps):
                    #         self.data['player']['status'] = self.hero_die_status
                    #         item['frame_count'] = 0
                    #         continue
                    #     else:
                    #         item['status'] = self.monster_attack_status
                    #         self.data["player"]["hp"] = 0
                    #         self.data['player']['status'] = self.hero_hit_bomb_status
                    #         item['frame_count'] += 1
                    #         continue

                    if item['dir'] == 'left':
                        if player_pos[0] >= (monster_pos[0] - 6) and player_pos[0] <= monster_pos[0]:
                            if self.data['player']['status'] == self.hero_heal_status:
                                self.data['player']['hit_status'] = 0   
                                item['frame_count'] += 1
                                if item['frame_count'] > 11:
                                    item['frame_count'] = 0
                                    item['status'] = self.monster_idle_status
                                continue

                            if item['frame_count'] == 0:
                                item['status'] = self.monster_attack_status
                                self.data["player"]["hp"] -= item['damage']
                                if self.data['player']['hp'] < 0:
                                    self.data['player']['hp'] = 0
                                self.data['player']['hit_status'] = 1
                                item['frame_count'] += 1
                                if self.data['player']['hp'] == 0:
                                    self.data['player']['status'] = self.hero_die_status
                                    item['frame_count'] = 9999
                                continue
                            elif item['frame_count'] <= 11:
                                item['frame_count'] += 1
                                continue
                            elif item['frame_count'] > 11 and item['frame_count'] < item['attack_speed']:
                                item['status'] = self.monster_idle_status
                                self.data['player']['hit_status'] = 0
                                item['frame_count'] += 1
                                continue
                            elif item['frame_count'] >= item['attack_speed']:
                                item['frame_count'] = 0
                                continue
                            
                    item['status'] = self.monster_run_status
                    if item['dir'] == 'right':
                        item['pos'][0] += 2/int(self.go_time * self.fps)
                    elif item['dir'] == 'left':
                        item['pos'][0] -= 2/int(self.go_time * self.fps)
                        # if monster_pos[0] > player_pos[0]:
                        #     item['pos'][0] -= 2/int(self.go_time * self.fps)
                        # elif monster_pos[0] < player_pos[0]:
                        #     item['pos'][0] += 2/int(self.go_time * self.fps)

                        # if monster_pos[1] > player_pos[1]:
                        #     item['pos'][1] -= 2/int(self.go_time * self.fps)
                        # elif monster_pos[1] < player_pos[1]:
                        #     item['pos'][1] += 2/int(self.go_time * self.fps)

    def batch_boss(self):
        if self.data['player']['status'] != self.hero_heal_status:
            self.time_count += 1

        if self.data['stage']['init_item_list'][0]['status'] == self.monster_die_status:
            return

        if self.data['stage']['init_item_list'][0]['bomb_count'] >= 8:
            self.data['player']['status'] = self.hero_die_status
            self.data['player']['hp'] = 0
            self.data['player']['hit_status'] = 2
            return

        if self.time_count >= 60:
            if self.data['stage']['init_item_list'][0]['attack_frame'] == 0:
                if self.data['stage']['init_item_list'][0]['status'] == self.monster_idle_status:
                    self.data['stage']['init_item_list'][0]['status'] = self.monster_attack_status
                self.data['stage']['init_item_list'][0]['bomb_count'] += 1
                self.data['stage']['init_item_list'][0]['attack_frame'] += 1

                items = self.data['stage']['init_item_list']
                available_ids = [item['id'] for item in items if item['status'] == 0]
                id = random.choice(available_ids)

                if self.data['stage']['diff'] == 'Hard' or self.data['stage']['diff'] == "Normal":
                    status = random.choices([1, 2], weights=[6, 4], k=1)[0]
                else:
                    status = 1

                if self.data['stage']['diff'] == 'Hard':
                    password_length = random.randint(2, 6)
                    password = [random.choice(range(2, 41, 2))] 
                    password.extend(random.randint(1, 40) for _ in range(password_length - 1))
                    answer = sum(num for num in password if num % 2 == 0)
                else:
                    password = []
                    answer = 1

                self.data['stage']['init_item_list'][id]['status'] = status
                self.data['stage']['init_item_list'][id]['password'] = password
                self.data['stage']['init_item_list'][id]['answer'] = answer

            elif self.data['stage']['init_item_list'][0]['attack_frame'] > 0 and self.data['stage']['init_item_list'][0]['attack_frame'] < 11 :
                self.data['stage']['init_item_list'][0]['attack_frame'] += 1

            elif self.data['stage']['init_item_list'][0]['attack_frame'] >= 11:
                if self.data['stage']['init_item_list'][0]['status'] == self.monster_attack_status:
                    self.data['stage']['init_item_list'][0]['status'] = self.monster_idle_status
                self.data['stage']['init_item_list'][0]['attack_frame'] = 0
                self.time_count = 0

            return
        

    def detect_item(self, item_type):
        if item_type == 'door':
            #return '문'
            return '문'
        elif item_type == 'bomb':
            return '폭탄'
        elif item_type == 'food':
            return '보급품'
        elif item_type == 'rocket_parts':
            return '로켓부품'
        elif item_type == 'laser_switch' or item_type == 'drop_switch' or item_type == 'variation_switch':
        #     return '스위치'
            return '없음'
        elif item_type == 'print_point':
            return 'Print 지점'
        elif item_type == 'info_point':
            return '정보 획득 지점'
        elif item_type == 'number_point':
            return '숫자 획득 지점'
        elif item_type == 'engines' or item_type == 'solid_propellant' or item_type == 'liquid_fuel':
            return '로켓부품 설치 지점'


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
        for item in self.data['stage']['init_item_list']:
            if item['type'] == 'bomb' and item['status'] != self.item_off_status and [new_x_middle, new_y_middle] == item['pos']: # 폭탄 밟음
                return 2
            
            elif item['type'] == 'door' and item['status'] == self.item_on_status and [new_x_middle, new_y_middle] == item['pos']: # 문에 막힘
                return 1
            
            elif item['type'] == 'laser' and item['status'] == self.item_on_status: # 레이저 닿음
                player_middle_pos_rounded = [new_x_middle, new_y_middle]
                
                if (item['dir'] == 'h' and player_middle_pos_rounded[1] == item["pos_start"][1] and # 가로레이저
                    item["pos_start"][0] <= player_middle_pos_rounded[0] <= item["pos_end"][0]):
                        return 3

                elif (item['dir'] == 'v' and player_middle_pos_rounded[0] == item["pos_start"][0] and # 세로 레이저
                    item["pos_start"][1] <= player_middle_pos_rounded[1] <= item["pos_end"][1]):
                        return 3
                
            elif item['type'] == 'aggressive_monster_2' and item['pos'][0] < new_x and item['pos'][0] > new_x_middle:
                return 4
                 
        # 중간위치 벽 체크 
        if self.data["stage"]["tile"][new_y_middle][new_x_middle] == 2:
            return 1
        
        return 0
    
    def can_jump(self):
        now_x = int(round(self.data["player"]["pos"][0]))
        now_y = int(round(self.data["player"]["pos"][1]))
        new_x = now_x
        new_y = now_y
        
        # 이동할 좌표 계산
        # 기본 이동 2칸씩 [new_x, new_y]
        # 중간지점 [new_x_middle, new_y_middle]
        if self.data["player"]["dir"] == "right":
            new_x = round(now_x + 4)
        elif self.data["player"]["dir"] == "left":
            new_x = round(now_x - 4)
        elif self.data["player"]["dir"] == "up":
            new_y = round(now_y - 4)
        elif self.data["player"]["dir"] == "down":
            new_y = round(now_y + 4)
        
        # 맵 밖으로 나가는지 체크
        if new_x < 0 or new_y < 0 or new_y >= len(self.data["stage"]["tile"]) or new_x >= len(self.data["stage"]["tile"][0]):
            return 1
        
        for item in self.data['stage']['init_item_list']:
            if item['type'] == 'door' and item['status'] == self.item_on_status and [new_x, new_y] == item['pos'] or self.data["stage"]["tile"][new_y][new_x] == 2:
                return 1

        # item_list 로 중간위치 체크
        step_x = 1 if new_x > now_x else -1
        step_y = 1 if new_y > now_y else -1

        x_range = range(now_x, new_x + step_x, step_x)
        y_range = range(now_y, new_y + step_y, step_y)

        if self.data["player"]["dir"] in ["left", "right"]:
            for x in x_range:
                result = self.check_path(x, now_y)
                if result != 0:
                    return result
        else:
            for y in y_range:
                result = self.check_path(now_x, y)
                if result != 0:
                    return result
                
        return 0

    
    def check_path(self, x, y):
        if self.data["stage"]["tile"][y][x] == 2:
            return 1

        for item in self.data['stage']['init_item_list']:
            if item['status'] == self.item_on_status:
                if item['type'] == 'bomb' and [x, y] == item['pos']:
                    return 2
                elif item['type'] == 'door' and [x, y] == item['pos']:
                    return 1
                elif item['type'] == 'laser':
                    if (item['dir'] == 'h' and y == item["pos_start"][1] and item["pos_start"][0] <= x <= item["pos_end"][0]) or \
                    (item['dir'] == 'v' and x == item["pos_start"][0] and item["pos_start"][1] <= y <= item["pos_end"][1]):
                        return 3
        return 0
    
    def decision_dir(self, directions): # 회전 후 방향 결정
        dir_index = directions.index(self.data["player"]["dir"]) 
        new_dir_index = (dir_index + 1) % len(directions)

        return directions[new_dir_index]
    
    def do_move(self, total_frames, line_num):
        self.data["player"]["status"] = self.hero_run_status

        if self.data["player"]["dir"] == "right":
            for i in range(int(total_frames)):
                if i == int(total_frames)//2:
                    self.handle_switch_status()
                self.move(2/int(self.go_time * self.fps), 0, line_num)

        elif self.data["player"]["dir"] == "left":
            for i in range(int(total_frames)):
                if i == int(total_frames)//2:
                    self.handle_switch_status()
                self.move(-2/int(self.go_time * self.fps), 0, line_num)

        elif self.data["player"]["dir"] == "up":
            for i in range(int(total_frames)):
                if i == int(total_frames)//2:
                    self.handle_switch_status()
                self.move(0, -2/int(self.go_time * self.fps), line_num)

        elif self.data["player"]["dir"] == "down":
            for i in range(int(total_frames)):
                if i == int(total_frames)//2:
                    self.handle_switch_status()
                self.move(0, 2/int(self.go_time * self.fps), line_num)   

        self.data["player"]["status"] = self.hero_idle_status

    def do_jump(self, total_frames, line_num):
        self.data["player"]["status"] = self.hero_jump_status

        if self.data["player"]["dir"] == "right":
            for i in range(int(total_frames)):
                self.move(4/int(self.go_time * self.fps), 0, line_num)

        elif self.data["player"]["dir"] == "left":
            for i in range(int(total_frames)):
                self.move(-4/int(self.go_time * self.fps), 0, line_num)

        elif self.data["player"]["dir"] == "up":
            for i in range(int(total_frames)):
                self.move(0, -4/int(self.go_time * self.fps), line_num)

        elif self.data["player"]["dir"] == "down":
            for i in range(int(total_frames)):
                self.move(0, 4/int(self.go_time * self.fps), line_num)   

        self.data["player"]["status"] = self.hero_idle_status

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

    def do_turn(self, line_num, directions):
        self.data["player"]["status"] = self.hero_turn_status
        self.turn_half(line_num)
        self.data["player"]["dir"] = self.decision_dir(directions)
        self.turn_half(line_num)
        self.data["player"]["status"] = self.hero_idle_status

            
    def go(self, line_num):
        move_ability = self.can_move()

        if move_ability == 0:
            # 총프레임수 = 이동시간(초) * 초당프레임수
            total_frames = int(self.go_time * self.fps)
            self.do_move(total_frames, line_num)

        elif move_ability == 1:
            total_frames = int(self.go_cannot_time * self.fps)
            # 결과값 저장  10:이동불가 말풍선
            self.data["player"]["status"] = self.hero_cannot_move_status
            for _ in range(total_frames):
                self.frame_append(line_num - start_line)
            self.data["player"]["status"] = 0

        elif move_ability == 4:
            total_frames = int(self.go_cannot_time * self.fps)
            # 결과값 저장  10:이동불가 말풍선
            self.data["player"]["status"] = self.hero_cannot_move_monster_status
            for _ in range(total_frames):
                self.frame_append(line_num - start_line)
            self.data["player"]["status"] = 0

        elif move_ability == 2: # 중간에 폭탄밟음
            move_frames = int(self.go_time * self.fps)
            self.do_move(move_frames/2, line_num)

            # self.handle_item_status('bomb', self.item_off_status)
            for item in self.data['stage']['init_item_list']:
                if item['type'] == 'bomb' and [round(pos) for pos in self.data["player"]["pos"]] == item['pos']:
                    self.handle_item_status(item['id'], self.item_off_status)
            
            self.data["player"]["hp"] = 0 if self.data["player"]["hp"] <= 50 else self.data["player"]["hp"] - 50

            self.hit_frame_append(line_num - start_line)

            if self.data["player"]["hp"] <= 0: # 플레이어 사망
                return
            
            self.do_move(move_frames/2, line_num)

        elif move_ability == 3: # 중간에 레이져 밟음 ToDo: 나중에 디버깅 및 수정 필요
            move_frames = int(self.go_time * self.fps)

            self.do_move(move_frames/2, line_num)

            self.data["player"]["hp"] = 0
            self.hit_frame_append(line_num - start_line)
            return

    def jump(self, line_num):
        if hero.data['stage']['step'] + hero.data['stage']['diff'] + str(hero.data['stage']['level']) in hero.hero_cannot_jump_stage:
            self.set_fail(hero.hero_cannot_jump_gravity_status, line_num)  
            return

        move_ability = self.can_jump()

        if move_ability == 0 or move_ability == 2: # 폭탄 또는 작은애들은 짬푸가능
            # 총프레임수 = 이동시간(초) * 초당프레임수
            total_frames = int(self.jump_time * self.fps)
            self.do_jump(total_frames, line_num)
 
        elif move_ability == 1 or move_ability == 3 or move_ability == 4: # 문, 벽, 레이저 짬푸불가능
            total_frames = int(self.go_cannot_time * self.fps)

            self.data["player"]["status"] = self.hero_cannot_jump_status

            for i in range(int(total_frames)):
                self.frame_append(line_num - start_line)

            self.data["player"]["status"] = 0

    def turnRight(self, line_num): 
        directions = ["right", "down", "left", "up"] 
        self.do_turn(line_num, directions)

    def turnLeft(self, line_num):
        directions = ["right", "up", "left", "down"] 
        self.do_turn(line_num, directions)

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

        if item == '폭탄':
            self.set_bomb(player_pos, (player_pos[0] + offset[0]/2, player_pos[1] + offset[1]/2), line_num)
            return

        for item_data in self.data["stage"]["init_item_list"]:
            if (item_data["type"] in item_types and
                tuple(item_data["pos"]) == target_pos and
                item_data["status"] == self.item_off_status): # ToDo: 이미 설치한 자리에 또 설치하려고 할때 처리

                if item_data["require_dir"] == player_dir:

                    if item_data["name"] == item:
                        self.handle_item_status(item_data["id"], self.item_on_status)
                        self.set_success(line_num, item)
                        return
                    else: 
                        self.set_fail(self.hero_miss_item_status, line_num)
                        return
                else:
                    self.set_fail(self.hero_miss_dir_status, line_num)
                    return

        self.set_fail(self.hero_miss_dir_status, line_num) # 지금은 여기서 처리됨

    def set_bomb(self, player_pos, target_pos, line_num):
        if self.is_boss_stage():
            self.set_fail(self.hero_miss_dir_status, line_num)
            return

        for item in self.data['stage']['init_item_list']:
            if item['type'] == 'bomb' and tuple(item['pos']) == target_pos:
                if item['status'] == self.item_on_status:
                    self.set_fail(self.hero_miss_dir_status, line_num)
                    return
                else:
                    if self.data['player']['bomb_count'] <= 0:
                        self.set_fail(self.hero_not_enough_bomb_status, line_num)
                        raise SystemExit
                        return
                    else:
                        self.data['player']['bomb_count'] -= 1
                        item['status'] = self.item_on_status
                        total_frames = int(self.set_time * self.fps)
                        self.data["player"]["status"] = self.hero_set_bomb_status
                        for _ in range(total_frames):
                            self.frame_append(line_num - start_line)
                        self.data["player"]["status"] = 0
                        return
                    
        self.set_fail(self.hero_miss_dir_status, line_num)


    def use(self, args, line_num):
        if args != '응급치료제' and args != '치료키트': # 인자 틀림 exception 발생
            raise ValueError('존재하지 않는 아이템')
        
        if args == '응급치료제': # 응급치료제 사용
            if 'medicine_count' in self.data['player']: # 응급치료제 갯수 확인
                if self.data['player']['medicine_count'] <= 0:
                    self.set_fail(self.hero_not_enough_medicine_status, line_num)
                    return
                else:
                    self.data['player']['medicine_count'] -= 1
                    random_value = random.randint(25, 40)
                    self.data['player']['status'] = self.hero_heal_status
                    for _ in range(random_value):
                        self.data['player']['hp'] += 1 if self.data['player']['hp'] < 100 else 0
                        self.frame_append(line_num - start_line)
                    self.data['player']['status'] = self.hero_idle_status
            else:
                self.set_fail(self.hero_not_enough_medicine_status, line_num)
                return
            
        elif args == '치료키트': # 치료키트 사용
            if 'advanced_medicine_count' in self.data['player']: # 응급치료제 갯수 확인
                if self.data['player']['advanced_medicine_count'] <= 0:
                    self.set_fail(self.hero_not_enough_medicine_status, line_num)
                    return
                else:
                    self.data['player']['advanced_medicine_count'] -= 1
                    random_value = 100 - self.data['player']['hp']
                    self.data['player']['status'] = self.hero_heal_status
                    for _ in range(random_value):
                        self.data['player']['hp'] += 1 if self.data['player']['hp'] < 100 else 0
                        self.frame_append(line_num - start_line)
                    self.data['player']['status'] = self.hero_idle_status
            else:
                self.set_fail(self.hero_not_enough_medicine_status, line_num)
                return

    def print(self, args, line_num): 
        dir_offsets = {
            'up': (0, -1),
            'down': (0, 1),
            'left': (-1, 0),
            'right': (1, 0)
        }

        player_dir = self.data["player"]["dir"]
        player_pos = [round(pos) for pos in self.data["player"]["pos"]]

        offset = dir_offsets.get(player_dir, (0, 0))
        target_pos = (player_pos[0] + offset[0], player_pos[1] + offset[1])

        for item in self.data['stage']['init_item_list']:
            if item['type'] == 'print_point' and item['require_dir'] == self.data["player"]["dir"] and tuple(item["pos"]) == target_pos: # 위치 검사
                target_id = item['id']
                print_value = str(args)
                self.hero_print_array.append(print_value)
                if (isinstance(args, int) or isinstance(args, str)) and item['print_type'] == 'string': # 인쇄할 아이템 검사
                    if args == item['print_count'][self.info_print_index][0]: # 인쇄할 아이템 검사 (순서보장을 위한 인덱싱) 
                        # for index, msg in enumerate(self.data['item_list'][0]): # 인쇄할 아이템 선택
                        #     if isinstance(args, int):
                        #         target = str(args)
                        #     else:
                        #         target = args
                        #     if msg == target:
                        #         target_status = index

                        self.print_success(target_id, self.monster_print_status, line_num)
                        item['print_count'][self.info_print_index].remove(args) # 인쇄한 아이템 제거
                        
                        if item['print_count'][self.info_print_index] == []: # 모두 인쇄 완료 시 작업 ( 해결 완료 시 )
                            if item['action_type'] == 'door': # 문 일떄
                                self.handle_item_status(item['action_id'][0], self.item_off_status)
                            elif item['action_type'] == 'bomb':
                                for i in range(len(item['action_id'])):
                                    self.handle_item_status(item['action_id'][i], self.item_on_status)
                            elif item['action_type'] == 'laser':
                                self.handle_laser_status(item['action_id']) 
                        return  
                    
                    else: 
                        item['print_count'][self.info_print_index] = item['require_print'][self.info_print_index]
                        if args == item['print_count'][self.info_print_index][0]:

                            self.print_success(target_id, self.monster_print_status, line_num)
                            item['print_count'][self.info_print_index].remove(args) # 인쇄한 아이템 제거

                            if item['print_count'][self.info_print_index] == []: # 모두 인쇄 완료 시 작업 ( 해결 완료 시 )
                                if item['action_type'] == 'door': # 문 일떄
                                    self.handle_item_status(item['action_id'][0], self.item_off_status)
                                elif item['action_type'] == 'bomb':
                                    for i in range(len(item['action_id'])):
                                        self.handle_item_status(item['action_id'][i], self.item_on_status)
                                elif item['action_type'] == 'laser':
                                    self.handle_laser_status(item['action_id']) 
                            return
                        
                        else:
                            item['print_count'][self.info_print_index] = item['require_print'][self.info_print_index]
                            self.print_fail(self.hero_miss_args_status, target_id, line_num)
                            return
                elif isinstance(args, list) and item['print_type'] == 'list':
                    if args == item['print_count'][self.info_print_index]: # 인쇄할 아이템 검사 (순서보장을 위한 인덱싱) 

                        self.print_success(target_id, self.monster_print_status, line_num)
                        item['print_count'][self.info_print_index] = [] # 인쇄한 아이템 제거
                        
                        if item['print_count'][self.info_print_index] == []: # 모두 인쇄 완료 시 작업 ( 해결 완료 시 )
                            if item['action_type'] == 'door': # 문 일떄
                                self.handle_item_status(item['action_id'][0], self.item_off_status)
                            elif item['action_type'] == 'bomb':
                                for i in range(len(item['action_id'])):
                                    self.handle_item_status(item['action_id'][i], self.item_on_status)
                            elif item['action_type'] == 'laser':
                                self.handle_laser_status(item['action_id']) 

                        return  
                    
                    else: 
                        item['print_count'][self.info_print_index] = item['require_print'][self.info_print_index]
                        if args == item['print_count'][self.info_print_index][0]:

                            self.print_success(target_id, self.monster_print_status, line_num)
                            item['print_count'][self.info_print_index].remove(args) # 인쇄한 아이템 제거

                            if item['print_count'][self.info_print_index] == []: # 모두 인쇄 완료 시 작업 ( 해결 완료 시 )
                                if item['action_type'] == 'door': # 문 일떄
                                    self.handle_item_status(item['action_id'][0], self.item_off_status)
                                elif item['action_type'] == 'bomb':
                                    for i in range(len(item['action_id'])):
                                        self.handle_item_status(item['action_id'][i], self.item_on_status)
                                elif item['action_type'] == 'laser':
                                    self.handle_laser_status(item['action_id']) 
                            return
                        
                        else:
                            item['print_count'][self.info_print_index] = item['require_print'][self.info_print_index]
                            self.print_fail(self.hero_miss_args_status, target_id, line_num)
                            return

                else:
                    item['print_count'][self.info_print_index] = item['require_print'][self.info_print_index]
                    self.print_fail(self.hero_miss_args_status, target_id, line_num)
                    return
                
            elif item['type'] == 'passive_monster' and item['require_dir'] == self.data["player"]["dir"] and tuple(item["pos"]) == target_pos and item['status'] == self.monster_idle_status:
                target_id = item['id']
                self.hero_print_array = []
                self.hero_print_array.append(args)
                if args == item['print_count'][self.info_print_index][0]: # 인쇄할 아이템 검사 (순서보장을 위한 인덱싱) 
                    self.print_success(target_id, self.monster_print_status, line_num)
                    item['print_count'][self.info_print_index].remove(args) # 인쇄한 아이템 제거
                    
                    if item['print_count'][self.info_print_index] == []: # 모두 인쇄 완료 시 작업 ( 해결 완료 시 )
                        hit_frames = int(self.hit_bomb_time * self.fps)
                        die_frames = int(self.die_time * self.fps)
                        item['status'] = self.monster_hit_status

                        for _ in range(hit_frames):
                            self.frame_append(line_num - start_line)
                        
                        item['status'] = self.monster_die_status

                        for _ in range(die_frames):
                            self.frame_append(line_num - start_line)
                    return  
            
                else: # 인쇄 실패, 몬스터는 회전 후 공격, 플레이어는 hit 후 사망 처리
                    self.print_fail(self.hero_miss_args_status, target_id, line_num)
                    turn_frames = int(self.turn_time * self.fps)
                    attack_frames = int(self.attack_time * self.fps)

                    item['dir'] = 'right' if item['dir'] == 'left' else 'left'
                    item['status'] = self.monster_turn_status

                    for _ in range(turn_frames):
                        self.frame_append(line_num - start_line)
                    
                    item['status'] = self.monster_attack_status
                    self.data["player"]["status"] = self.hero_hit_bomb_status

                    for _ in range(attack_frames):
                        self.frame_append(line_num - start_line)

                    item['status'] = self.monster_idle_status
                    self.data["player"]["hp"] = 0
                    self.data["player"]["status"] = self.hero_die_status

                    self.frame_append(line_num - start_line)

                    return
                
        self.set_fail(self.hero_miss_dir_status, line_num)

    def getItem(self, line_num):
        dir_offsets = {
            'up': (0, -1),
            'down': (0, 1),
            'left': (-1, 0),
            'right': (1, 0)
        }

        player_dir = self.data["player"]["dir"]
        player_pos = [round(pos) for pos in self.data["player"]["pos"]]

        offset = dir_offsets.get(player_dir, (0, 0))
        target_pos = (player_pos[0] + offset[0], player_pos[1] + offset[1])

        for item in self.data['stage']['init_item_list']:
            if item['type'] == 'box' and item['require_dir'] == self.data["player"]["dir"] and tuple(item["pos"]) == target_pos and item['status'] == self.item_on_status and item['count'] > 0: # 위치 검사
                if item['box_type'] == "string":
                    item['count'] -= 1
                    if item['count'] == 0:
                        item['status'] = self.item_off_status
                    return self.get_item_frame(item, line_num)   
                
                else:
                    miss_frames = int(self.print_fail_time * self.fps)
                    self.data['player']['status'] = self.hero_miss_type_status
                    for _ in range(miss_frames):
                        self.frame_append(line_num - start_line)
                    self.data['player']['status'] = self.hero_idle_status

                    return ""
                        
        self.set_fail(self.hero_miss_dir_status, line_num)
        return ""
    
    def getItemList(self, line_num): 
        dir_offsets = {
            'up': (0, -1),
            'down': (0, 1),
            'left': (-1, 0),
            'right': (1, 0)
        }

        player_dir = self.data["player"]["dir"]
        player_pos = [round(pos) for pos in self.data["player"]["pos"]]

        offset = dir_offsets.get(player_dir, (0, 0))
        target_pos = (player_pos[0] + offset[0], player_pos[1] + offset[1])

        for item in self.data['stage']['init_item_list']:
            if item['type'] == 'box' and item['require_dir'] == self.data["player"]["dir"] and tuple(item["pos"]) == target_pos and item['status'] == self.item_on_status: # 위치 검사
                if item['box_type'] == "list":
                    item['status'] = self.item_off_status
                    return self.get_item_frame(item, line_num)
                
                else:
                    miss_frames = int(self.print_fail_time * self.fps)
                    self.data['player']['status'] = self.hero_miss_type_status
                    for _ in range(miss_frames):
                        self.frame_append(line_num - start_line)
                    self.data['player']['status'] = self.hero_idle_status

                    return []
                        
        self.set_fail(self.hero_miss_dir_status, line_num)
        return []


    def getInfo(self, line_num):
        dir_offsets = {
            'up': (0, -1),
            'down': (0, 1),
            'left': (-1, 0),
            'right': (1, 0)
        }

        player_dir = self.data["player"]["dir"]
        player_pos = [round(pos) for pos in self.data["player"]["pos"]]

        offset = dir_offsets.get(player_dir, (0, 0))
        target_pos = (player_pos[0] + offset[0], player_pos[1] + offset[1])

        for item in self.data['stage']['init_item_list']:
            if item['type'] == 'info_point' and item['require_dir'] == self.data["player"]["dir"] and tuple(item["pos"]) == target_pos: # 위치 검사
                return self.get_info_frame(item, line_num)
                

            if item['type'] == 'passive_monster' and item['require_dir'] == self.data["player"]["dir"] and tuple(item["pos"]) == target_pos and item['status'] == self.monster_idle_status:
                return self.get_info_frame(item, line_num)
                
        self.set_fail(self.hero_miss_dir_status, line_num)
        return ""

    def getNumber(self, line_num):
        dir_offsets = {
            'up': (0, -1),
            'down': (0, 1),
            'left': (-1, 0),
            'right': (1, 0)
        }

        player_dir = self.data["player"]["dir"]
        player_pos = [round(pos) for pos in self.data["player"]["pos"]]

        offset = dir_offsets.get(player_dir, (0, 0))
        target_pos = (player_pos[0] + offset[0], player_pos[1] + offset[1])

        for item in self.data['stage']['init_item_list']:
            if item['type'] == 'number_point' and item['require_dir'] == self.data["player"]["dir"] and tuple(item["pos"]) == target_pos: # 위치 검사
                total_frames = int(self.get_info_time * self.fps)

                self.data["player"]["status"] = 100 + stage["item_list"].index(item['info'][self.info_print_index])

                for _ in range(total_frames):
                    self.frame_append(line_num - start_line)
                    
                self.data["player"]["status"] = self.hero_idle_status

                return item['info'][self.info_print_index]  
            
            elif item['type'] == 'monster_info' and item['require_dir'] == self.data["player"]["dir"] and tuple(item["pos"]) == target_pos and item['status'] == self.item_on_status and item['count'] > 0: # 위치 검사
                if item['box_type'] == "string":
                    if len(item['item'][self.info_print_index]) == 1:
                        item['status'] = self.item_off_status
                    return self.get_item_frame(item, line_num)   
                
                else:
                    miss_frames = int(self.print_fail_time * self.fps)
                    self.data['player']['status'] = self.hero_miss_type_status
                    for _ in range(miss_frames):
                        self.frame_append(line_num - start_line)
                    self.data['player']['status'] = self.hero_idle_status

                    return 0

        self.set_fail(self.hero_miss_dir_status, line_num)
        return 0
    
    def getNumberList(self, line_num): 
        dir_offsets = {
            'up': (0, -1),
            'down': (0, 1),
            'left': (-1, 0),
            'right': (1, 0)
        }

        player_dir = self.data["player"]["dir"]
        player_pos = [round(pos) for pos in self.data["player"]["pos"]]

        offset = dir_offsets.get(player_dir, (0, 0))
        target_pos = (player_pos[0] + offset[0], player_pos[1] + offset[1])

        for item in self.data['stage']['init_item_list']:
            if item['type'] == 'monster_info' and item['require_dir'] == self.data["player"]["dir"] and tuple(item["pos"]) == target_pos and item['status'] == self.item_on_status: # 위치 검사
                if item['box_type'] == "list":
                    # item['status'] = self.item_off_status
                    return self.get_item_frame(item, line_num)
                
                else:
                    miss_frames = int(self.print_fail_time * self.fps)
                    self.data['player']['status'] = self.hero_miss_type_status
                    for _ in range(miss_frames):
                        self.frame_append(line_num - start_line)
                    self.data['player']['status'] = self.hero_idle_status

                    return []
                        
        self.set_fail(self.hero_miss_dir_status, line_num)
        return []

    
    def get_info_frame(self, item, line_num):
        total_frames = int(self.get_info_time * self.fps)

        self.data["player"]["status"] = 100 + stage["item_list"].index(item['info'][self.info_print_index])

        for _ in range(total_frames):
            self.frame_append(line_num - start_line)
            
        self.data["player"]["status"] = self.hero_idle_status

        return item['info'][self.info_print_index]
    
    def get_item_frame(self, item, line_num):
        total_frames = int(self.get_info_time * self.fps)

        if item['box_type'] == "string":
            self.data["player"]["status"] = 100 + stage["item_list"].index(item['item'][self.info_print_index][0])
            return_value = item['item'][self.info_print_index][0]
            item['item'][self.info_print_index].remove(item['item'][self.info_print_index][0])
        elif item['box_type'] == "list":
            self.data["player"]["status"] = 100 + stage["item_list"].index(item['item'][self.info_print_index])
            return_value = item['item'][self.info_print_index]


        for _ in range(total_frames):
            self.frame_append(line_num - start_line)
            
        self.data["player"]["status"] = self.hero_idle_status

        return return_value


    def detect_obstacle(self, direction):
        dir_offsets = {
            'up': (0, -1),
            'down': (0, 1),
            'left': (-1, 0),
            'right': (1, 0)
        }

        player_dir = direction
        player_pos = [round(pos) for pos in self.data["player"]["pos"]]

        offset = dir_offsets.get(player_dir, (0, 0))
        target_pos = (player_pos[0] + offset[0], player_pos[1] + offset[1])

        dir_offsets2 = {
            'up': (0, -2),
            'down': (0, 2),
            'left': (-2, 0),
            'right': (2, 0)
        }

        offset2 = dir_offsets2.get(player_dir, (0, 0))
        target_pos2 = (player_pos[0] + offset2[0], player_pos[1] + offset2[1])

        if target_pos2[0] >= len(self.data['stage']['tile'][0]) or target_pos2[1] >= len(self.data['stage']['tile']) or target_pos2[0] < 0 or target_pos2[1] < 0: # 맵 밖
            return "벽"

        if target_pos[0] > len(self.data['stage']['tile'][0]) or target_pos[1] > len(self.data['stage']['tile']) or target_pos[0] < 0 or target_pos[1] < 0: # 맵 밖
            return "벽"
        if self.data['stage']['tile'][target_pos[1]][target_pos[0]] == 2: # 벽
            return "벽"
        for item in self.data['stage']['init_item_list']: # 바로 앞 (한칸)
            if item['type'] == 'tile_damage':
                continue
            if item['type'] == 'laser':  # 레이저는 따로 처리
                if item['status'] == 0:
                    continue

                pos_start = tuple(item['pos_start'])
                pos_end = tuple(item['pos_end'])
                
                if item['dir'] == 'v' and pos_start[0] == target_pos[0]:
                    if min(pos_start[1], pos_end[1]) <= target_pos[1] <= max(pos_start[1], pos_end[1]):
                        return '레이저'
                
                elif item['dir'] == 'h' and pos_start[1] == target_pos[1]:
                    if min(pos_start[0], pos_end[0]) <= target_pos[0] <= max(pos_start[0], pos_end[0]):
                        return '레이저'

            #레이져 말고
            elif tuple(item["pos"]) == target_pos and item['status'] != self.item_off_status:
                return self.detect_item(item['type'])
            
        if self.data['stage']['tile'][target_pos2[1]][target_pos2[0]] == 2: # 벽
            return "벽"
        for item in self.data['stage']['init_item_list']: # 바로 앞 (한칸)
            if item['type'] == 'tile_damage':
                continue
            if item['type'] == 'laser':  # 레이저는 따로 처리
                if item['status'] == 0:
                    continue

                pos_start = tuple(item['pos_start'])
                pos_end = tuple(item['pos_end'])
                
                if item['dir'] == 'v' and pos_start[0] == target_pos2[0]:
                    if min(pos_start[1], pos_end[1]) <= target_pos2[1] <= max(pos_start[1], pos_end[1]):
                        return '레이저'
                
                elif item['dir'] == 'h' and pos_start[1] == target_pos2[1]:
                    if min(pos_start[0], pos_end[0]) <= target_pos2[0] <= max(pos_start[0], pos_end[0]):
                        return '레이저'

            #레이져 말고
            elif tuple(item["pos"]) == target_pos2 and item['status'] == self.item_on_status:
                return self.detect_item(item['type'])
            
        return "없음"
    
    def detect_obstacle_far(self):
        dir_offsets = {
            'up': (0, -4),
            'down': (0, 4),
            'left': (-4, 0),
            'right': (4, 0)
        }

        dir_offsets2 = {
            'up': (0, -3),
            'down': (0, 3),
            'left': (-3, 0),
            'right': (3, 0)
        }

        player_dir = self.data["player"]["dir"]
        player_pos = [round(pos) for pos in self.data["player"]["pos"]]

        offset = dir_offsets.get(player_dir, (0, 0))
        offset2 = dir_offsets2.get(player_dir, (0, 0))
        target_pos = (player_pos[0] + offset[0], player_pos[1] + offset[1])
        target_pos2 = (player_pos[0] + offset2[0], player_pos[1] + offset2[1])

        if target_pos[0] > len(self.data['stage']['tile'][0]) or target_pos[1] > len(self.data['stage']['tile']) or target_pos[0] < 0 or target_pos[1] < 0:
            return '없음'
        
        for item in self.data['stage']['init_item_list']:
            if item['type'] == 'bomb':
                if (tuple(item['pos']) == target_pos or tuple(item['pos']) == target_pos2) and item['status'] != self.item_off_status:
                    return '폭탄'
            # elif item['type'] == 'bomb' and item['pos'] == target_pos2 and item['status'] != self.item_off_status:
            #     return '폭탄'

        return '없음'
    
    def attack(self, args, line_num):
        player_pos = [round(pos) for pos in self.data["player"]["pos"]]

        for item in self.data['stage']['init_item_list']:
            if (item['type'] == 'aggressive_monster_2' and item['status'] != self.monster_die_status and item['status'] != self.monster_invisible_status and 
                item['hp'] > 0 and player_pos[0] < item['pos'][0] and player_pos[0] + 16 > item['pos'][0]):
                if item['name'] == args:
                    item['hp'] -= 20
                    if item['hp'] <= 0:
                        item['hp'] = 0

                    item['status'] = self.monster_hit_status
                    self.append_attack_frame(line_num)
                    return

        self.append_attack_frame(line_num)

    def append_attack_frame(self, line_num):
        attack_frames = int(self.attack_time * self.fps)
        self.data['player']['status'] = self.hero_attack_status
        for _ in range(attack_frames):
            self.frame_append(line_num - start_line)
        self.data['player']['status'] = self.hero_idle_status
        for _ in range(30 - attack_frames):
            self.frame_append(line_num - start_line)

    def findEnemy(self):
        player_pos = [round(pos) for pos in self.data["player"]["pos"]]
        closest_monster = None
        min_position_x = float('inf')  

        for item in self.data['stage']['init_item_list']:
            if (item['type'] == 'aggressive_monster_2' and 
                item['status'] != self.monster_die_status and item['status'] != self.monster_invisible_status and
                item['hp'] > 0 and 
                player_pos[0] <= item['pos'][0] ): #<= player_pos[0] + 16
                
                if item['pos'][0] < min_position_x:
                    min_position_x = item['pos'][0]
                    closest_monster = item['name']

        return closest_monster if closest_monster is not None else '없음'
    
    def getHp(self, name):
        if name is None:
            return self.data["player"]["hp"]
        else:
            for item in self.data['stage']['init_item_list']:
                if item['type'] == 'aggressive_monster_2' and item['name'] == name and item['status'] != self.monster_die_status and item['status'] != self.monster_invisible_status:
                    return item['hp']
                
        return -1
    
    def get(self, arg, line_num):
        if not self.is_boss_stage():
            return 

        dir_offsets = {
            'up': (0, -1),
            'down': (0, 1),
            'left': (-1, 0),
            'right': (1, 0)
        }

        player_dir = self.data["player"]["dir"]
        player_pos = [round(pos) for pos in self.data["player"]["pos"]]

        offset = dir_offsets.get(player_dir, (0, 0))
        target_pos = (player_pos[0] + offset[0], player_pos[1] + offset[1])

        if arg == '폭탄':
            for item in self.data['stage']['init_item_list']:
                if item['type'] == 'bomb' and item['status'] != self.item_off_status and tuple(item['pos']) == target_pos:
                    if self.data['player']['boss_bomb'] >= 1:
                        self.set_fail(self.hero_enough_energy_status, line_num)
                        return []

                    if item['status'] == self.item_on_status:
                        if item['password'] != []:
                            item['status'] = 0
                            self.data['player']['boss_bomb'] = item['answer']
                            self.data['stage']['init_item_list'][0]['bomb_count'] -= 1
                            self.data['player']['status'] = self.hero_get_energy_status
                            get_frames = int(self.get_info_time * self.fps)
                            for _ in range(get_frames):
                                self.frame_append(line_num - start_line)
                            self.data['player']['status'] = self.hero_idle_status
                            return item['password']
                        else:
                            item['status'] = 0
                            self.data['player']['boss_bomb'] = 1
                            self.data['stage']['init_item_list'][0]['bomb_count'] -= 1
                            self.data['player']['status'] = self.hero_get_energy_status
                            get_frames = int(self.get_info_time * self.fps)
                            for _ in range(get_frames):
                                self.frame_append(line_num - start_line)
                            self.data['player']['status'] = self.hero_idle_status
                            return []
                    elif item['status'] == 2:
                        item['status'] = 0
                        self.data['stage']['init_item_list'][0]['bomb_count'] -= 1
                        self.data['player']['status'] = self.hero_get_energy_status
                        get_frames = int(self.get_info_time * self.fps)
                        for _ in range(get_frames):
                            self.frame_append(line_num - start_line)
                        self.data['player']['status'] = self.hero_hit_bomb_status
                        self.data['player']['hp'] -= self.boss_bomb_damage
                        if self.data['player']['hp'] <= 0:
                            self.data['player']['hp'] = 0
                            self.die_frame_append()
                            return []  
                        hit_frames = int(self.hit_bomb_time * self.fps)
                        for _ in range(hit_frames):
                            self.frame_append(line_num - start_line)
                        self.data['player']['status'] = self.hero_idle_status
                        return []           
            
                    
        self.set_fail(self.hero_miss_dir_status, line_num)
        return []
    
    def chargeShot(self, answer, line_num):
        if not self.is_boss_stage():
            return
        
        if self.data['player']['boss_bomb'] == 0:
            self.set_fail(self.hero_not_enough_energy_status, line_num)
            return
        else: 
            if self.data['player']['boss_bomb'] == answer:
                self.data['player']['boss_bomb'] = 0
                self.data['player']['status'] = self.hero_shoot_status
                self.data['stage']['init_item_list'][0]['status'] = self.monster_hit_status
                self.data['stage']['init_item_list'][0]['hit_count'] += 1
                self.time_count = 0
                attack_frames = int(self.attack_time * self.fps)
                if self.data['stage']['init_item_list'][0]['hit_count'] >= 3:
                    self.data['stage']['init_item_list'][0]['status'] = self.monster_die_status
                    for _ in range(attack_frames):
                        self.frame_append(line_num - start_line)
                    return
                for _ in range(attack_frames):
                    self.frame_append(line_num - start_line)
                self.data['player']['status'] = self.hero_idle_status
                self.data['stage']['init_item_list'][0]['status'] = self.monster_idle_status
                return
            else:
                self.data['player']['boss_bomb'] = 0
                self.data['player']['status'] = self.hero_fail_resolved_status
                self.data['player']['hp'] -= self.boss_bomb_damage  
                if self.data['player']['hp'] <= 0:
                    self.data['player']['hp'] = 0
                    self.die_frame_append()
                    return   
                hit_frames = int(self.hit_bomb_time * self.fps)
                for _ in range(hit_frames):
                    self.frame_append(line_num - start_line)
                self.data['player']['status'] = self.hero_idle_status
                return 






    def checkFront(self, line_num):
        return self.detect_obstacle(self.data["player"]["dir"])
    
    def checkRight(self, line_num):
        directions = ["right", "down", "left", "up"]
        return self.detect_obstacle(self.decision_dir(directions))
    
    def checkLeft(self, line_num):
        directions = ["right", "up", "left", "down"]
        return self.detect_obstacle(self.decision_dir(directions))
    
    def check_goal(self, line_num): # 목적지 상태 (상호작용) 체크
        if self.data["player"]["hp"] <= 0: # 플레이어 사망
            self.data["player"]["status"] = self.hero_die_status 
            self.die_frame_append()
            return
        
        player_pos = [round(pos) for pos in self.data["player"]["pos"]]
        for item in self.data['stage']['init_item_list']: 
            if item['type'] == 'tile_damage':
                damage = item['value']

        if self.data['stage']['tile'][player_pos[1]][player_pos[0]] == 3 and self.frames[-2]["player"]["pos"] != self.data["player"]["pos"]: # 불판 위에 서있음
            self.data["player"]["hp"] -= damage if damage else 0
            self.data['player']['status'] = self.hero_on_hit_tile_status
            if self.data["player"]["hp"] <= 0: # 플레이어 사망
                self.data['player']['hp'] = 0
                self.frame_append(line_num - start_line)
                self.die_frame_append()
                return
            else :
                self.frame_append(line_num - start_line)
                self.data['player']['status'] = self.hero_idle_status
            
            
        for item in self.data['stage']['init_item_list']: # 아이템리스트 상호작용 시작
            if item['type'] == 'food' and player_pos == item['pos'] and item['status'] == self.item_on_status: # food 획득
                self.data["player"]["food_count"] += 1
                self.handle_item_status(item['id'], self.item_off_status)
                self.data["player"]["status"] = self.hero_get_item_status
                self.frame_append(line_num - start_line)
                self.data["player"]["status"] = self.hero_idle_status

            elif item['type'] == 'rocket_parts' and player_pos == item['pos'] and item['status'] == self.item_on_status: # 로켓부품 획득
                self.data["player"]["rocket_parts_count"] += 1
                self.handle_item_status(item['id'], self.item_off_status)
                self.data["player"]["status"] = self.hero_get_item_status
                self.frame_append(line_num - start_line)
                self.data["player"]["status"] = self.hero_idle_status

            elif item['type'] == 'bomb' and player_pos == item['pos'] and item['status'] != self.item_off_status: # 폭탄 밟음
                self.handle_item_status(item['id'], self.item_off_status)
                
                self.data["player"]["hp"] = 0 if self.data["player"]["hp"] <= 50 else self.data["player"]["hp"] - 50
                self.hit_frame_append(line_num - start_line)

                if self.data["player"]["hp"] <= 0: # 플레이어 사망
                    self.die_frame_append()
                    return


            elif item['type'] == 'laser_switch' and player_pos == item['pos'] and self.frames[-2]["player"]["pos"] != self.data["player"]["pos"]:
                self.handle_item_status(item['id'], 0)
                active_laser_ids = []  # new version
                for laser_id in item["laser_id"]:  # 스위치 작동
                    active_laser_ids.append(laser_id) # new version

                self.handle_laser_status(active_laser_ids) # new version
                self.frame_append(line_num - start_line) # new version
                            
            elif item['type'] == 'laser' and item['status'] == 1: # 레이저 닿음
                player_pos_rounded = round(self.data["player"]["pos"][0]), round(self.data["player"]["pos"][1])

                if (item['dir'] == 'h' and player_pos_rounded[1] == item["pos_start"][1] and # 가로레이저
                    item["pos_start"][0] <= player_pos_rounded[0] <= item["pos_end"][0]):
                        self.data["player"]["hp"] = 0
                        self.hit_frame_append(line_num - start_line)
                        self.die_frame_append()
                        return

                elif (item['dir'] == 'v' and player_pos_rounded[0] == item["pos_start"][0] and # 세로 레이저
                    item["pos_start"][1] <= player_pos_rounded[1] <= item["pos_end"][1]):
                        self.data["player"]["hp"] = 0
                        self.hit_frame_append(line_num - start_line)
                        self.die_frame_append()
                        return
                
            elif item['type'] == 'drop_switch': # 드롭 스위치 작동
                if player_pos == item['pos'] and self.frames[-2]["player"]["pos"] != self.data["player"]["pos"]:
                    if item['status'] != 2:
                        if item['count'] > 0:
                            item['count'] -= 1
                            self.handle_item_status(item['id'], 0) # 스위치 밟음, 카운트가 남아서 아이템 등장
                        
                        else:
                            self.handle_item_status(item['id'], 1) # 스위치 밟음, 카운트가 없어서 아이템 등장안함

                        self.frame_append(line_num - start_line)    
                    elif item['status'] == 2:
                        self.handle_item_status(item['id'], 0)
                        self.frame_append(line_num - start_line)

                elif player_pos == item['pos_drop'] and item['status'] == 2:
                    self.data["player"][item['drop_type']+'_count'] += 1
                    self.handle_item_status(item['id'], 3) # 아이템 획득 
                    self.data["player"]["status"] = self.hero_get_item_status
                    self.frame_append(line_num - start_line)
                    self.data["player"]["status"] = self.hero_idle_status

            elif item['type'] == 'variation_switch':
                if player_pos == item['pos'] and self.frames[-2]["player"]["pos"] != self.data["player"]["pos"] and item['status'] == 1:
                    item['status'] = 0
                    selected_variation = random.choice(item['variations'])

                    for item in self.data['stage']['init_item_list']:
                        if 'variation_no' in item and selected_variation in item['variation_no']:
                            item['status'] = 1
                        if item['type'] == 'door' and item['status'] == 1:
                            self.handle_item_status(item['id'], 0)
                        if item['type'] == 'laser' and item['status'] == 1:
                            self.handle_item_status(item['id'], 0)

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
                "player": self.make_player(),
                "item_list": self.make_item_list()
            })

    def check_monster(self, line_num):
        if self.data['player']['status'] == self.hero_die_status:
            return True

        for goal in self.data['stage']['goal_list']:
            self.batch_aggressive_monster_1_action = True 
            if goal['goal'] == 'enemy' and self.data['player']['status'] != self.hero_die_status:
                in_aggressive_monster = any (
                    item['type'] == 'aggressive_monster_1' or item['type'] == 'aggressive_monster_2' or item['type'] == 'boss'
                    for item in self.data['stage']['init_item_list']
                )
                if in_aggressive_monster:
                    # 게임이 종료되지 않았고, 플레이와 몬스터 모두 살아있을때 게임종료 방지
                    monsters_alive = any(
                        item["status"] != self.monster_die_status 
                        for item in self.data["stage"]["init_item_list"]
                        if item["type"] in ["passive_monster", "aggressive_monster_1", "aggressive_monster_2", "boss"]
                    )

                    if monsters_alive:
                        self.frame_append(0)
                        return False
                    else:
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
                                "player": self.make_player(),
                                "item_list": self.make_item_list()
                            })
                        return True
                    
            return True

    def check(self, item_list, line_num):
        self.data['player']['player_check_array'] = item_list
        if not item_list:
            empty_frames = int(self.check_time * self.fps)
            self.data['player']['status'] = self.hero_check_empty_status
            for _ in range(empty_frames):
                self.frame_append(line_num - start_line)
            self.data['player']['status'] = self.hero_idle_status

            return

        for item in self.data['stage']['goal_list']:
            if item['goal'] == 'List':
                total_frames = int(self.check_time * self.fps)
                self.data['player']['status'] = self.hero_check_status
                all_achieved = True
                for goal in self.data["stage"]["goal_list"]:
                    if not self.check_goal_achieved(goal, line_num):
                        if goal['goal'] == 'List':
                            self.data['player']['status'] = self.hero_check_fail_status

                        all_achieved = False
                        break  

                for _ in range(total_frames):
                    self.frame_append(line_num - start_line)
                self.data['player']['status'] = self.hero_idle_status
                self.data['player']['player_check_array'] = []

                if all_achieved:
                    self.frames.append({
                        "id": len(self.frames),
                        "status": 1,
                        "line_num": 0,
                        "player": self.make_player(),
                        "item_list": self.make_item_list()
                    })

                return

            check_frames = int(self.check_time * self.fps)
            self.data['player']['status'] = self.hero_check_status
            for _ in range(check_frames):
                self.frame_append(line_num - start_line)
            self.data['player']['status'] = self.hero_idle_status
            self.data['player']['player_check_array'] = []

            
    def check_goal_achieved(self, goal, line_num): # 게임 목표 체크 함수
        if goal['goal'] == 'target':
            return goal["pos"] == [round(pos) for pos in self.data["player"]["pos"]]
        elif goal['goal'] == 'item' and goal['type'] == 'food':
            return goal["count"] <= self.data["player"]["food_count"]
        elif goal['goal'] == 'item' and goal['type'] == 'rocket_parts':
            return goal["count"] <= self.data["player"]["rocket_parts_count"]
        elif goal['goal'] == 'line':
            # self.line_counter = max(self.line_counter, line_num - start_line)
            # return goal["count"] >= self.line_counter
            return True
        
        elif goal['goal'] == 'set':
            count = sum(1 for item in self.data["stage"]['init_item_list'] if item["type"] == goal['type'] and item["status"] == 1)
            return count >= goal['count']
        elif goal['goal'] == 'enemy':
            # 모든 몬스터가 죽었는지 확인
            all_monsters_dead = all(
                item["status"] == self.monster_die_status
                for item in self.data["stage"]["init_item_list"]
                if item["type"] in ["passive_monster", "aggressive_monster_1", "aggressive_monster_2", "boss"]
            )
            return all_monsters_dead
        elif goal['goal'] == 'List':
            return Counter(goal['require_list'][self.info_print_index]) == Counter(self.data['player']['player_check_array'])

        return False 
    
    def check_game_status(self): # 게임 종료 체크
        if self.frames:
            if self.frames[-1]["player"]["status"] == self.hero_die_status or self.frames[-1]["status"] == 1:
                raise SystemExit
                return True

    def get_frames(self):
        return self.frames
    
    def create_data(self):
        frames = self.get_frames()  
        num_frames = len(frames)  

        with open('frames_output.txt', 'w') as f:
            for i, frame in enumerate(frames):
                frame_str = str(frame).replace("'", '"')
                
                if i < num_frames - 1:
                    frame_str += ","
                
                print(frame_str, file=f)

def go(num = 1):
    for _ in range(num):
        # print("go")
        if(hero.check_game_status()):
            return
        hero.go(inspect.currentframe().f_back.f_lineno)
        hero.check_goal(inspect.currentframe().f_back.f_lineno)

def jump(num = 1):
    for _ in range(num):
        # print("jump")
        if(hero.check_game_status()):
            return
        hero.jump(inspect.currentframe().f_back.f_lineno)
        hero.check_goal(inspect.currentframe().f_back.f_lineno)

def turnRight(num = 1):
    for _ in range(num):
        # print("turnRight")
        if(hero.check_game_status()):
            return
        hero.turnRight(inspect.currentframe().f_back.f_lineno)
        hero.check_goal(inspect.currentframe().f_back.f_lineno)
    
def turnLeft(num = 1):
    for _ in range(num):
        # print("turnLeft")
        if(hero.check_game_status()):
            return
        hero.turnLeft(inspect.currentframe().f_back.f_lineno)
        hero.check_goal(inspect.currentframe().f_back.f_lineno)

def checkFront():
    if(hero.check_game_status()):
        return
    return hero.checkFront(inspect.currentframe().f_back.f_lineno)

def checkRight():
    if(hero.check_game_status()):
        return
    return hero.checkRight(inspect.currentframe().f_back.f_lineno)

def checkLeft():
    if(hero.check_game_status()):
        return
    return hero.checkLeft(inspect.currentframe().f_back.f_lineno)

def checkFar():
    if(hero.check_game_status()):
        return
    return hero.detect_obstacle_far()

def check(item_list):
    if(hero.check_game_status()):
        return
    return hero.check(item_list, inspect.currentframe().f_back.f_lineno)

def set(item):
    # print("set("+item+")")
    if(hero.check_game_status()):
        return
    hero.set(item, inspect.currentframe().f_back.f_lineno)
    hero.check_goal(inspect.currentframe().f_back.f_lineno)

def print(args):
    if(hero.check_game_status()):
        return
    hero.print(args, inspect.currentframe().f_back.f_lineno)
    hero.check_goal(inspect.currentframe().f_back.f_lineno)

def getInfo():
    if(hero.check_game_status()):
        return
    return hero.getInfo(inspect.currentframe().f_back.f_lineno)

def getNumber():
    if(hero.check_game_status()):
        return
    return hero.getNumber(inspect.currentframe().f_back.f_lineno)

def getNumberList():
    if(hero.check_game_status()):
        return
    return hero.getNumberList(inspect.currentframe().f_back.f_lineno)

def getItem():
    if(hero.check_game_status()):
        return
    return hero.getItem(inspect.currentframe().f_back.f_lineno)

def getItemList():
    if(hero.check_game_status()):
        return
    return hero.getItemList(inspect.currentframe().f_back.f_lineno)

def getHp(name=None):
    if(hero.check_game_status()):
        return -1
    return hero.getHp(name)

def get(arg):
    if(hero.check_game_status()):
        return
    return hero.get(arg, inspect.currentframe().f_back.f_lineno)

def use(args):
    if(hero.check_game_status()):
        return
    hero.use(args, inspect.currentframe().f_back.f_lineno)

def attack(args):
    if(hero.check_game_status()):
        return
    hero.attack(args, inspect.currentframe().f_back.f_lineno)
    hero.check_goal(inspect.currentframe().f_back.f_lineno)

def findEnemy():
    if(hero.check_game_status()):
        return
    return hero.findEnemy()

def chargeShot(answer=1):
    if hero.check_game_status():
        return
    hero.chargeShot(answer, inspect.currentframe().f_back.f_lineno)
    hero.check_goal(inspect.currentframe().f_back.f_lineno)
`;
  
const logic2 = 
`
hero = Character(stage)

# player input start  
start_line = inspect.currentframe().f_lineno + 1
try:
`;

const logic3 =
`
except SystemExit:
    pass

while not hero.check_monster(0):
    pass  

frames = hero.get_frames()  
json.dumps(frames)     
`

addEventListener('message', async (event) => {
    if (!self.pyodide) {
        importScripts('/pyodide.js');
        self.pyodide = await loadPyodide({
            indexURL: "/pyodide/"
        });
    }

    try {
        const { stageData, userInput } = event.data;

        input = userInput.split('\n').map(line => `    ${line}`).join('\n');

        const result = await self.pyodide.runPythonAsync(`${logic1}${stageData}${logic2}${input}${logic3}`);

        postMessage({ result });
    } catch (error) {
        if (error instanceof Error) {
            postMessage({ error: error.message }); 
        } else {
            postMessage({error: "알 수 없는 오류 발생"});
        }
    }
});