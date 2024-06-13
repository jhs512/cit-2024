


const Controller = require("../Controller");
const SoundManger = require("./SoundManager");
const Loader = require("./Loader");
const SoundManager = require("./SoundManager");

cc.Class({
    extends: cc.Component,

    properties: {
        btnTest: cc.Button,
        // 로딩 화면
        loadingBG: cc.Node,

        isLoaded : false,
        isCameraShaked : false,

        // 코드가 현재 플레이중인 상태를 저장하는 변수
        isPlay: false,

        // 메인 카메라
        camera : cc.Camera,

        // 게임 시작전 강제로 포지션 오프셋값 주는 배열 변수
        mapOffset : {
            default:[],
        },

        // 현재 읽고 있는 id 값 뜻함.
        idx: 0,

        /**
         * 실제 사용하는 객체 모음
         */
        // 맵 객체
        gameMap: cc.TiledMap,
        // 플레이어 객체
        player : cc.Node,
        // 우주선 파츠 부모 객체
        //파츠는 해당 객체 아래에 추가됨.
        spaceShip : cc.Node,
        // 배경화면 뿌려지는곳.
        bgNode: cc.Node,

        // Floor 객체 Parent
        floorParent : cc.Node,


        //플레이어, 맵을 제외한 월드에 위치 하는 객체를 담는 배열
        item: [],

        // 드롭 아이템 리스트
        dropItemList: [],
        // 드롭스위치 리스트
        dropSwitchList:[],

        // 게임 최종도착지
        goalObject: cc.Node,

        // 객체노드.
        objectParent: cc.Node,

        effectParent: cc.Node,

        audioManager: cc.Node,



        // 픽업 이펙트 담는 리스트
        pList : [],
        pIdx : 0,

        // 회복 이펙트 담는 리스트
        hList : [],
        hIdx : 0,

        // 이펙트 담는 리스트
        expList: [],
        expIdx : 0,

        // 아이템 떨어질때 이펙트 리스트
        dropEffectList: [],
        dropEffectIdx : 0,


        // 폭발 이펙트 중인지 체크
        isPlayExplosion : false,


    },

    /**
     * 게임에서 사용될 이미지로드 SingleTon 로드가 된걸 확인한 뒤
     * 로딩 화면을 감춰주는 Interval 생성
     */
    onLoad(){
        this.loadingBG.active = true;

        /**
         * + 이미지 및 맵까지 로드가 되면 로딩을 푸느게 좋지않을까 (추후 조정.)
         */
        var self = this;
        var inter = setInterval(function(){
            if(Loader.getInstance().GetImage(0) != null){
                clearInterval(inter);

                self.InitGame();
            }
        },90);

    },

    start(){
        /**
         * 오디오 로드 테스함.
         * @type {number}
         */
        var audioInter = setInterval(()=>{

            if(SoundManger.getInstance().IsLoadCheck() != null){
                clearInterval(audioInter);
            }
        },30);

    },

    /**
     * 큰 타일맵 로드시 맵이 짤려보여는 현상을 해결하기 위해 맵을 2초에 걸쳐 맵을 좌우로 이동시켜주는 효과
     * 해당 효과로 맵 랜더링 오류가 해결
     */
    _TileMapShake: function(){
        var s = this;
        setTimeout(function(){
            s.CameraMoveX(1);
        },1000);

        setTimeout(function(){
            s.CameraMoveX(-1);
            s.LoadingFadeOut();
        },2000);

        /*
        로딩 페이드아웃.
         */
    },

    /**
     * mapShake
     * @param idx
     * @constructor
     */
    CameraMoveX : function(idx){
        const currentPosition = this.camera.node.position;
        const newXPosition = currentPosition.x - idx;

        var v3 = cc.v3(newXPosition, currentPosition.y, currentPosition.z);

        this.camera.node.setPosition(v3);
    },


    /**
     * 로딩 검은 화면 FadeOut 해주는 함수.
     * Init관련된건 이미 처리하고 Fade out 처리해줌.
     * @constructor
     */
    LoadingFadeOut: function(){
        var self = this;
        var offset = 5;



        setTimeout(()=>{
            var loadingInterval = setInterval(function(){

                if(self.loadingBG.opacity <= 0){
                    offset++;
                    self.loadingBG.active = false;
                    self.isLoaded = true;
                    clearInterval(loadingInterval);
                    Controller.getInstance().FinalLoadDone();

                }
                self.loadingBG.opacity -= offset;
            },30);
        },1000);
    },


    /**
     * 게임 초기화 해주는 함수입니다.
     * @constructor
     */
    InitGame: function(){
        var self = this;

        this.mapOffset = [
            // T-1
            { x: 0, y: 2 },
            // T-2
            { x: 0, y: 1 },
            // 1-1-E
            { x: 0, y: 1 },
            { x: 0, y: 1 },
            { x: 0, y: 1 },

            // 1-1-N
            { x: 0, y: 1 },
            { x: 0, y: 1 },
            { x: 0, y: 1 },

            // 1-1-H
            { x: 0, y: 1 },
            { x: 0, y: 1 },
            { x: 0, y: 1 },


            // 1-2-E
            { x: 0, y: 1 },
            { x: 0, y: -1 },
            { x: 0, y: -1 },

            // 1-2-N
            { x: 0, y: 1 },
            { x: 0, y: -1 },
            { x: 0, y: -1 },

            // 1-2-H
            { x: 0, y: 1 },
            { x: 0, y: -1 },
            { x: 0, y: -1 },


            // 1-3-E
            { x: 1, y: -2 },
            { x: 0, y: -1 },
            { x: -4, y: 1 },

            // 1-3-N
            { x: -1, y: -2 },
            { x: 0, y: -1 },
            { x: -4, y: 1 },

            //1-3-H
            { x: -1, y: -2 },
            { x: 0, y: -1 },
            { x: -4, y: 1 },
        ];
        this.loadInit();
    },

    /**
     *  Json 데이터가 로드가 정상적으로 되었는지 Interval을 이용하여 확인한다.
     */
    loadInit: function(){

        var self = this;
        // Interver 이용하여 로드 체크한다.
        var inter = setInterval(function(){

            if(Controller.getInstance().initJson != null){
                self.EffectInit();
                self.InitMap();
                // self.InitFloor();
                self.InitPlayer();
                self.InitObject();

                // 카메라 초기화
                self.InitialCamera();

                clearInterval(inter);
            }

        }, 100);
    },


    //TODO EFFECT
    ShakeEffect: function() {
        if(this.isCameraShaked) return;
        this.isCameraShaked = true;

        var intensity = cc.v2(5, 0);

        // 초기 위치 저장
        const initialPos = this.camera.node.position.clone();
        // 일정 시간 동안 흔들림 애니메이션
        this.camera.node.runAction(
            cc.sequence(
                cc.moveBy(0.02, intensity),
                cc.moveBy(0.04, intensity.neg()),
                cc.moveBy(0.02, intensity.neg()),
                cc.moveBy(0.04, intensity.neg()),
                cc.moveTo(0, initialPos),
            )
        );
    },

    /**
     *  게임 Update 코드 상태값을 확인하여 코드를 동작시키는 함수
     *  0 값이면 동작하지않고,
     *  1 이면 동작하도록 Controller Status 값으로 확인.
     * @param dt
     */
    update(dt){
        if(!this.isLoaded) return;

        var status = Controller.getInstance().GetStatus();
        if(status){
            this.OnCodePlay();
        }
    },


    /**
     * 플레이어 초기화 해주는 함수 입니다.
     * @constructor
     */
    InitPlayer: function(){
        var initPlayerObject = Controller.getInstance().getInitPlayerData();

        var initPos = initPlayerObject.pos;
        var playerDir = initPlayerObject.dir;
        var initHp = initPlayerObject.hp;
        var playerStatus = initPlayerObject.status;
        var foodCount = initPlayerObject.food_count;
        var rocket_parts_count = initPlayerObject.rocket_parts_count;

        var self = this;

        var initvector = this.GVector(initPos[0],initPos[1]);
        //var initvector = this.GVector(0,0);

        // 2.3.x 버전
        cc.loader.loadRes('./prefabs/Player', cc.Prefab, function (err, prefab) {
            // 리소스 로드가 완료된 후 실행할 코드
            if (err) {
                cc.error("Error loading image: " + err);
                return;
            }
            // 로드된 SpriteFrame 사용
            self.player = cc.instantiate(prefab);
            self.player.getComponent("Player").Init(initvector, playerDir);

            // 현재 스크립트가 추가되어 있는 노드에 플레이어 노드를 추가합니다.
            self.node.addChild(self.player);
        });
    },

    /**
     *
     * Json coordination -> Cocos game Coordination
     *
     * 실제 게임 벡터로 변환 해주는 함수
      * @param x 입력되는 X좌표 값입니다.
     *  @param y 입력되는 Y좌표 값입니다.
     * @constructor
     */
    GVector : function (x, y) {

        var lX = x * 1;
        var lY = y * -1;

        var stageObject = Controller.getInstance().getInitStageData();

        var step = stageObject.step;
        var diff = stageObject.diff;
        var level = stageObject.level;

        var gameLevel = this.ConvertGameLevel(step,diff,level);


        var v2 = cc.v2(
            ((lX + this.mapOffset[gameLevel].x) * Env.OFFSET_X) + Env.PLAYER_RADIO,
            ((lY - this.mapOffset[gameLevel].y) * Env.OFFSET_Y) - Env.PLAYER_RADIO
        );
        return v2;
    },

    /**
     * Json 정보를 토대로 맵 오프셋값을 가져옵니다.
     * @param step json 단계
     * @param diff json 난이도  Easy , Normal , Hard
     * @param level Json 레벨
     * @returns {number}  컨버팅된 게임 레벨값
     * @constructor
     */
    ConvertGameLevel: function(step,diff,level){

        var gameLevel = 0;

        if(step === "tutorial"){
            gameLevel =  level - 1;
        }
        else{
            // 1-1-E
            if(step === "1-1" && diff === "Easy" && level === 1){ gameLevel = 2; }
            if(step === "1-1" && diff === "Easy" && level === 2){ gameLevel = 3; }
            if(step === "1-1" && diff === "Easy" && level === 3){ gameLevel = 4; }

            // 1-1-N
            if(step === "1-1" && diff === "Normal" && level === 1){ gameLevel = 5; }
            if(step === "1-1" && diff === "Normal" && level === 2){ gameLevel = 6; }
            if(step === "1-1" && diff === "Normal" && level === 3){ gameLevel = 7; }

            // 1-1-H
            if(step === "1-1" && diff === "Hard" && level === 1){ gameLevel = 8; }
            if(step === "1-1" && diff === "Hard" && level === 2){ gameLevel = 9; }
            if(step === "1-1" && diff === "Hard" && level === 3){ gameLevel = 10; }

            // 1-2-E
            if(step === "1-2" && diff === "Easy" && level === 1){ gameLevel = 11; }
            if(step === "1-2" && diff === "Easy" && level === 2){ gameLevel = 12; }
            if(step === "1-2" && diff === "Easy" && level === 3){ gameLevel = 13; }

            // 1-2-N
            if(step === "1-2" && diff === "Normal" && level === 1){ gameLevel = 14; }
            if(step === "1-2" && diff === "Normal" && level === 2){ gameLevel = 15; }
            if(step === "1-2" && diff === "Normal" && level === 3){ gameLevel = 16; }

            // 1-2-H
            if(step === "1-2" && diff === "Hard" && level === 1){ gameLevel = 17; }
            if(step === "1-2" && diff === "Hard" && level === 2){ gameLevel = 18; }
            if(step === "1-2" && diff === "Hard" && level === 3){ gameLevel = 19; }

            // 1-3-E
            if(step === "1-3" && diff === "Easy" && level === 1){ gameLevel = 20; }
            if(step === "1-3" && diff === "Easy" && level === 2){ gameLevel = 21; }
            if(step === "1-3" && diff === "Easy" && level === 3){ gameLevel = 22; }

            // 1-3-N
            if(step === "1-3" && diff === "Normal" && level === 1){ gameLevel = 23; }
            if(step === "1-3" && diff === "Normal" && level === 2){ gameLevel = 24; }
            if(step === "1-3" && diff === "Normal" && level === 3){ gameLevel = 25; }

            // 1-3-H
            if(step === "1-3" && diff === "Hard" && level === 1){ gameLevel = 26; }
            if(step === "1-3" && diff === "Hard" && level === 2){ gameLevel = 27; }
            if(step === "1-3" && diff === "Hard" && level === 3){ gameLevel = 28; }
        }
        return gameLevel;
    },


    /**
     * 맵정보를 로드하고, 초기화 합니다.
     * @constructor
     */
    InitMap: function(){
        var mapurl = this.GetMapURL();
        var self = this;

        var url = "./map/" + mapurl;

        // // 2.3.x 버전
        cc.loader.loadRes(url, cc.TiledMapAsset, function (err, tmx_file) {
            // 리소스 로드가 완료된 후 실행할 코드
            if (err) {
                cc.error("Error loading image: " + err);
                return;
            }
            self.gameMap.tmxAsset = tmx_file;

            /**
             * 아래 에서 맵사이즈를 계산하여 해당 포지션 만큼 카메라 포지션 셋팅해줍니다.
             */
            var mapSize = self.gameMap.getMapSize();
            var tileSize = self.gameMap.getTileSize();

            var mapWidth = mapSize.width * tileSize.width;
            var mapHeight = mapSize.height * tileSize.height;

            var v2 = cc.v2(-mapWidth*1.5, -mapHeight*3);

            self.gameMap.node.setPosition(0,-mapHeight * 3);
            self._TileMapShake();

            /**
             * 여기서 추가 적으로페이드 아웃을 걸자..
             */



        });
    },


    /**
     * Json Stage 정보를 토대로 저장된 맵 프리팹 url 리턴합니다.
     * @returns {string}
     * @constructor
     */
    GetMapURL : function(){
        var url = "";
        var stageObject = Controller.getInstance().getInitStageData();

        var map  = stageObject.map;
        var step = stageObject.step;
        var diff = stageObject.diff;
        var level = stageObject.level;


        if(step === "tutorial"){

            url = "map_T-"+level.toString();
        }
        else{
            var cLevel = "Easy";
            switch (diff){
                case "Easy": cLevel = "E"; break;
                case "Normal" : cLevel = "N"; break;
                case "Hard" : cLevel = "H"; break;
            }
            url = "map_"+step.toString() + "-" + cLevel + "-" + level.toString();
        }
        return url;
    },


    /**
     * 카메라 포지션을 조정합니다.
     * @constructor
     * @param x X좌표
     * @param y Y좌표
     */
    SetCamera : function(x = 0, y = 0, zoomLevel = 1.5){
        // 메인 카메라의 포지션 설정
        this.camera.node.setPosition(cc.v3(x, y, 0)); // 여기서 x, y, z는 포지션의 좌표값입니다.
        this.camera.zoomRatio = zoomLevel;
    },


    /**
     * 맵의 갯수가 많지 않으니 하드 코딩처리되어있습니다.
     * 카메라 포지셔닝, 맵의 오프셋값 이 정의 되어있습니다.
     * @constructor
     */
    InitialCamera: function() {

        // Default option
        // -600 -860  1
        var stageObject = Controller.getInstance().getInitStageData();

        var step = stageObject.step;
        var diff = stageObject.diff;
        var level = stageObject.level;

        var gameLevel = this.ConvertGameLevel(step,diff,level);

        this.spaceShip.active = false;


        console.log(gameLevel);


        switch (gameLevel){
            case 0 : case 1:
                this.SetCamera(-650,-870,1.4);
                break;
            case 2: case 3:
            case 5: case 6:
            case 8: case 9:
                this.SetCamera(-600,-970,1.2);
                break;
            case 4: case 7: case 10:
                this.SetCamera(-400,-1060,1);
                break;

            case 11: case 14: case 17:
                this.SetCamera(-300,-1200,0.75);
                break;
            case 12: case 15: case 18:
                this.SetCamera(350,-1150,0.7);
                break;
            case 13: case 16: case 19:
                this.SetCamera(620,-1150,0.58);
                break;

            case 20: case 23: case 26:
                this.SetCamera(140,-900,0.8);
                this.spaceShip.setPosition(cc.v2(-4830,600));
                this.spaceShip.active = true;
                break;


            case 21: case 24: case 27:
                this.SetCamera(350,-900,0.7);
                this.spaceShip.setPosition(cc.v2(-550, 600));
                this.spaceShip.active = true;
                break;

            case 22: case 25: case 28:
                this.SetCamera(350,-1300,0.7);
                this.spaceShip.setPosition(cc.v2(5260,-780));
                this.spaceShip.active = true;
                break;
        }
        //this.camera.node.setPosition()
    },


    /**
     * 이펙트 초기화 해주는 함수
     * 게임의 진행에 따라 Call 될수 있습니다.
     * @constructor
     */
    EffectInit: function(){
        var self = this;
        var url = '/prefabs/explosion';
        var healUrl = "/prefabs/heal";
        var pickUrl = "/prefabs/pickup";
        var dropEffectUrl = "/prefabs/dropEffect";


        for(var i = 0; i < 5; i++) {
            cc.loader.loadRes(url, cc.Prefab, function (err, effect) {
                // 리소스 로드가 완료된 후 실행할 코드
                if (err) {
                    cc.error("Error loading image: " + err);
                    return;
                }

                var n1 = cc.instantiate(effect);
                n1.active = false;
                self.effectParent.addChild(n1);

                self.expList.push(n1);

            });

            cc.loader.loadRes(healUrl, cc.Prefab, function (err, effect) {
                // 리소스 로드가 완료된 후 실행할 코드
                if (err) {
                    cc.error("Error loading image: " + err);
                    return;
                }

                var n1 = cc.instantiate(effect);
                n1.active = false;

                self.effectParent.addChild(n1);

                self.hList.push(n1);

            });

            cc.loader.loadRes(pickUrl, cc.Prefab, function (err, effect) {
                // 리소스 로드가 완료된 후 실행할 코드
                if (err) {
                    cc.error("Error loading image: " + err);
                    return;
                }

                var n1 = cc.instantiate(effect);
                n1.active = false;

                self.effectParent.addChild(n1);
                self.pList.push(n1);

            });


            cc.loader.loadRes(dropEffectUrl, cc.Prefab, function (err, effect) {
                // 리소스 로드가 완료된 후 실행할 코드
                if (err) {
                    cc.error("Error loading image: " + err);
                    return;
                }

                var n1 = cc.instantiate(effect);
                n1.active = false;

                self.effectParent.addChild(n1);
                self.dropEffectList.push(n1);
            });
        }

    },

    /**
     * 폭발 이펙트 보여주는 함수입니다.
     * @param pos  포지션값 GVector() 사용 변환 포지션
     * @constructor
     */
    ShowExplosion: function(pos){

        if(this.expIdx >= this.expList.length-1  ) this.expIdx = 0;
        this.expList[this.expIdx].active = true;

        this.expList[this.expIdx].setPosition(pos);

        var animation = this.expList[this.expIdx].getComponent(cc.Animation);

        var self = this;
        animation.on('finished',function(){

            if(self.expList[self.expIdx].active){
                self.expList[self.expIdx].active = false;
                self.expIdx++;
            }
        },this);
        self.isCameraShaked = false;


        animation.play("explosion");
    },

    /**
     * 아이템이 회복 이펙트 효과  추가
     * @param pos 포지션값 GVector() 사용 변환 포지션
     * @constructor
     */
    ShowHeal: function(pos){

        if(this.hIdx >= this.hList.length-1  ) this.hIdx = 0;

        this.hList[this.hIdx].active = true;
        this.hList[this.hIdx].setPosition(pos);

        var animation = this.hList[this.hIdx].getComponent(cc.Animation);

        var self = this;
        animation.on('finished',function(){
            if(self.hList[self.expIdx].active) {
                self.hList[self.hIdx].active = false;
                self.hIdx++;
            }
        },this);

        animation.play("heal");
        this.hList[this.hIdx].getComponent(cc.Animation).play("heal");
    },

    /**
     * 아이템이 획득 이펙트 효과  추가
     * @param pos 포지션값 GVector() 사용 변환 포지션
     * @constructor
     */
    ShowPickup: function(pos){

        if(this.pIdx >= this.pList.length-1  ) this.pIdx = 0;

        this.pList[this.pIdx].active = true;
        this.pList[this.pIdx].setPosition(pos);

        var animation = this.pList[this.pIdx].getComponent(cc.Animation);

        var self = this;
        animation.on('finished',function(){
            if(self.pList[self.pIdx].active){
                self.pList[self.pIdx].active = false;
                self.pIdx++;
            }

        },this);
        SoundManager.getInstance().PlaySfx(Env.SFX_EARN_ITEM);
        animation.play("pickup");
    },


    /**
     * 아이템이 드롭될때 이펙트 효과  추가
     * @param pos 포지션값 GVector() 사용 변환 포지션
     * @constructor
     */
    ShowDropEffect: function(pos){

        if(this.dropEffectIdx >= this.dropEffectList.length-1 ) this.dropEffectIdx = 0;

        this.dropEffectList[this.dropEffectIdx].active = true;

        this.dropEffectList[this.dropEffectIdx].setPosition(pos);

        var animation = this.dropEffectList[this.dropEffectIdx].getComponent(cc.Animation);

        var self = this;
        animation.on('finished',function(){

            if(self.dropEffectList[self.dropEffectIdx].active){
                self.dropEffectList[self.dropEffectIdx].active = false;
                self.dropEffectIdx++;
            }

        },this);

        animation.play("dropeffect");
    },


    /**
     * 맵위에 출력될 객체 초기화합니다.
     * @constructor 
     */
    InitObject: function() {

        var goalList = Controller.getInstance().getInitStageData().goal_list;
        this.MakeGoal(goalList);

        var objects = Controller.getInstance().getInitOjbectDatas();

        if (objects.length < 1) {
            console.log("item list is Null");
            return;
        } else {
            for (var i = 0; i < objects.length; i++) {
                this.MakeUpObject(objects[i]);
            }
        }
//        console.log(this.item[0].getComponent("Gobject").GetItemID());

    },

    /**
     * 골 지점 객체를 생성합니다.
     * @param object 골객체 관련.
     * @constructor
     */
    MakeGoal: function(object){
        for(var j = 0; j < object.length; j++){
            if(object[j].goal === "target"){
                var targets = object[j];
                var goalPos = cc.v2(targets.pos[0], targets.pos[1]);
                this.AddPrefabs(Env.GOAL, -1, goalPos);
                break;
            }
        }

    },

    /**
     * 게임의 전반적인 모든 객체를 만드는 함수
     * 해당 함수는 총괄 함수는 컨트롤하는 함수입니다.
     *
      * @param type 객체의 타입을 받습니다.
     * @constructor
     */
    MakeUpObject: function(object){
        var type = object.type;

        if(type === "laser"){

            var id = object.id;
            var dir = object.dir;
            var startX = object.pos_start[0];
            var startY = object.pos_start[1];
            var status = object.status;

            var startPos = cc.v2(startX, startY);
            this.AddLaserPrefab(Env.LASER_START_ON, id, startPos , status , dir );



            var endX = object.pos_end[0];
            var endY = object.pos_end[1];

            var endPos = cc.v2(endX, endY);
            this.AddLaserPrefab(Env.LASER_END_ON, id, endPos, status, dir );

            /**
             * 중간 레이저 포인터 가져오기.
             */
            if(dir === 'h'){
                // 가로
                for(var i = startX+1 ; i < endX ; i++){
                    var p = cc.v2(i, startY);
                    this.AddLaserPrefab(Env.LASER_MIDDLE_ON, id, p , status, dir);
                }
            }
            else if(dir === "v"){
                // 세로
                for(var i = startY+1 ; i < endY ; i++){
                    var p = cc.v2(startX, i);
                    this.AddLaserPrefab(Env.LASER_MIDDLE_ON, id, p , status, dir);
                }
            }
        }
        else if(type === 'drop_switch'){
            // drop Switch
            var switchId = object.id;
            var switchTag = this.NameToTag(object.type);
            var switchX = object.pos[0];
            var switchY = object.pos[1];

            var switchPos = cc.v2(switchX, switchY);

            var dropX = object.pos_drop[0];
            var dropY = object.pos_drop[1];
            var dropType = object.drop_type;
            var dropItemTag = this.NameToTag(dropType);

            var dropItemPos = cc.v2(dropX, dropY);
            this.AddDropSwitchPrefabs(switchTag, switchId, switchPos, dropItemTag,  dropItemPos )

        }
        else{

            var tag = this.NameToTag(object.type);

            var posX = object.pos[0];
            var posY = object.pos[1];
            var itemId = object.id;
            var pos = cc.v2(posX, posY);


            if(tag === Env.ROCKET_EMPTY){
                this.AddRocketParts(tag,itemId,pos, object);
            }
            else{
                this.AddPrefabs(tag, itemId,  pos );
            }
        }

    },

    /**
     * 입력된 String 값을 게임에서 사용되는 태그로바꿔주는 함수
     * @param str 입력된 Json type값
     * @constructor
     */
    NameToTag: function(str){

        switch(str){
            case "food" : return Env.FOOD;
            case "bomb": return Env.BOMB;
            case "rocket_parts" : return Env.BATTERY;
            case "drop_switch" : return Env.NORMAL_SWITCH_ON;
            case "laser_switch": return Env.LASER_SWITCH_ON;
            case "engines": case "solid_propellant": case "liquid_fuel":  return Env.ROCKET_EMPTY;
        }
    },

    /**
     * 로켓파츠 프리팹 만들어주는 함수
     * @param tag  로켓파츠의 태그
     * @param id  객체 아이디
     * @param pos  GVector 의 포지션
     * @param object  json Stream  (방향정보를 가져오기위함)
     * @constructor
     */
    AddRocketParts: function(tag, id, pos, object){
        var prefabName = "rocketParts";
        var self = this;

        var itemurl = "./prefabs/" + prefabName;

        cc.loader.loadRes(itemurl, cc.Prefab, function (err, prefabs) {
            // 리소스 로드가 완료된 후 실행할 코드
            if (err) {
                cc.error("Error loading image: " + err);
                return;
            }

            var n1 = cc.instantiate(prefabs);
            n1.addComponent("Gobject");
            n1.getComponent('Gobject').Init(tag,id);

            var v1 = self.GVector(pos.x,pos.y);
            n1.setPosition(v1);

            // n1.opacity = 100;

            switch (object.require_dir){
                case "up": n1.rotation = 0; break;
                case "down": n1.rotation = -180; break;
                case "left": n1.rotation = -90; break;
                case "right": n1.rotation = 90; break;
            }

            self.item.push(n1);
            // self.node.addChild(n1);
            self.objectParent.addChild(n1);
            self.objectParent.setLocalZOrder = 10;
        });
    },

    /**
     * 프리팹을 생성해줍니다.
     * @param tag  객체의 태그값 Env 전역넘버
     * @param id  Json 에서 불러온 id 값
     * @param pos  cc.v2 포지션값
     * @constructor
     */
    AddPrefabs: function(tag , id , pos ){
        var prefabName = "";
        var self = this;

        // 태그값에 해당하는 프리팹 이름 가져옴.
        switch (tag){
            case Env.FOOD : prefabName = "food";    break;
            case Env.NORMAL_SWITCH_ON : case Env.NORMAL_SWITCH_OFF : prefabName = "nSwitch";  break;
            case Env.LASER_SWITCH_ON : case Env.LASER_SWITCH_OFF : prefabName = "lSwitch"; break;
            case Env.BATTERY : prefabName = "battery"; break;
            case Env.BOMB : prefabName = "bomb"; break;
            case Env.ROCKET_EMPTY : case Env.ROCKET_FILLED : prefabName = "rocketParts";  break;
            case Env.GOAL : prefabName = "goal"; break;
            case Env.FLOOR: prefabName = "floor"; break;
        }
        var itemurl = "./prefabs/" + prefabName;

        cc.loader.loadRes(itemurl, cc.Prefab, function (err, prefabs) {
            // 리소스 로드가 완료된 후 실행할 코드
            if (err) {
                cc.error("Error loading image: " + err);
                return;
            }

            var n1 = cc.instantiate(prefabs);
            var v1 = self.GVector(pos.x,pos.y);
            n1.setPosition(v1);
            n1.addComponent("Gobject");
            n1.getComponent('Gobject').Init(tag,id);

            if(tag === Env.GOAL){
                self.goalObject = n1;
                self.node.addChild(n1);
            }
            else{
                self.item.push(n1);
                self.node.addChild(n1);

            }
        });
    },

    /**
     * 드롭 스위치 프리팹 만들어주는 함수
     * @param tag  태그값 (드롭 스위치 태그) Env 참고
     * @param id  객체의 아이디값
     * @param pos  GVector 를 활용한 함수
     * @param drop_item_tag  드롭 아이템의 태그
     * @param drop_item_pos  드롭 아이템 포지션
     * @constructor
     */
    AddDropSwitchPrefabs : function(tag, id, pos , drop_item_tag, drop_item_pos){
        var self = this;
        var dropSwitchPrefabs = "nSwitch";
        var dropItemPrefabs = "battery";

        var switchUrl = "./prefabs/" + dropSwitchPrefabs;

        cc.loader.loadRes(switchUrl, cc.Prefab, function (err, prefabs) {
            // 리소스 로드가 완료된 후 실행할 코드
            if (err) { cc.error("Error loading image: " + err); return; }

            var n1 = cc.instantiate(prefabs);
            n1.addComponent("Gobject");
            n1.getComponent('Gobject').Init(tag, id);

            var v1 = self.GVector(pos.x,pos.y);
            n1.setPosition(v1);
            self.node.addChild(n1);

            self.dropSwitchList.push(n1);
        });

        var dropUrl = './prefabs/' + dropItemPrefabs;
        cc.loader.loadRes(dropUrl, cc.Prefab, function (err, prefabs) {
            // 리소스 로드가 완료된 후 실행할 코드
            if (err) { cc.error("Error loading image: " + err); return; }

            var n1 = cc.instantiate(prefabs);
            n1.addComponent("Gobject");
            n1.getComponent('Gobject').Init(drop_item_tag, id);
            n1.getComponent("Gobject").DropItemHide(true);


            var v1 = self.GVector(drop_item_pos.x,drop_item_pos.y);
            n1.setPosition(v1);
            self.node.addChild(n1);

            self.dropItemList.push(n1);

        });
    },


    /**
     * 레이저 객체 프리팹을 만들어주는 함수
     * @param tag  레이저 태그
     * @param id  id
     * @param pos  방향
     * @constructor
     */
    AddLaserPrefab: function(tag, id, pos , status, dir){
        var prefabName = "laser";
        var self = this;


        // 레이저 중간 객체라면 아래처럼 이미지 교체.
        if(tag === Env.LASER_MIDDLE_ON)  prefabName = "laserMiddle";

        var itemurl = "./prefabs/" + prefabName;

        cc.loader.loadRes(itemurl, cc.Prefab, function (err, prefabs) {
            // 리소스 로드가 완료된 후 실행할 코드
            if (err) { cc.error("Error loading image: " + err); return; }
            var n1 = cc.instantiate(prefabs);

            n1.addComponent("Gobject");
            n1.getComponent('Gobject').Init(tag, id);

            var isStatus = false;


            if(status === 0)  isStatus = false;
            else  isStatus = true;

            n1.getComponent("Gobject").LaserInitial(tag,isStatus , dir);

            var v1 = self.GVector(pos.x,pos.y);
            n1.setPosition(v1);
            self.node.addChild(n1);
            self.item.push(n1);
        });
    },

    /**
     * PYthon 코드 대로 동작을 실행합니다.
     * 실제 플레이어 및  모든 객체 상태를 변화를 줍니다.
     * 단 return 값을 false 하면 이동종료를 뜻합니다.
     * @returns {boolean}
     */
    executeCommand: function(id = 0){
        var command = Controller.getInstance().getCommandLine(id);


        /**
         * 명령어 종료를 뜻함
         */
        if(command === -1) return false;
        if(command.status === 1) return false;

        // 객체 상태값 업데이트
        this.ObjectUpdate(command.item_list);

        // 플레이어 상태값
        var playerStatus = command.player.status;


        var convertPos = this.GVector(command.player.pos[0], command.player.pos[1]);
        this.EffectControl(playerStatus, convertPos);

        this.player.getComponent("Player").setPlayerStatus(playerStatus);
        this.player.getComponent("Player").setDirection(command.player.dir);
        this.player.getComponent("Player").Movement(convertPos);
        return true;
    },

    /**
     * 폭탄, 픽업 아이템 보여주는 함수
     * @param status 현재 상태값
     * @param pos 포지션 GVector로 적용해야함
     * @constructor
     */
    EffectControl: function(status , pos){
        var self = this;

        if(status === 11){
            this.ShakeEffect();
            this.ShowExplosion(pos);

        }
        else if( status === 3){
            this.ShowPickup(pos);
        }
    },



    /**
     * 스트림 데이터를 입력받아 맵위에 있는 객체의 상태를 변화 하거나
     * Sprite를 변경해주는 업데이트 함수.
     * @param id_list
     * @constructor
     */
    ObjectUpdate: function(id_list) {
        // 비어 있다면 실행하지 않습니다.
        if(id_list.length < 1) return;

        for(var i = 0; i < id_list.length; i++){
            // 아이템 스테이터스 업데이트
            this.ItemStatusUpdate(i, id_list[i]);
            // Drop Switch UPdate
            this.DropSwitchUpdate(i, id_list[i]);
        }
    },


    /**
     * 드롭 스위치 의 상태를 표현해주는 함수입니다.
     * @param index  현재 상태 리스트
     * @param status 상태값을 찾습니다.
     * @constructor
     */
    DropSwitchUpdate: function(index, status){
        // 드랍 스위치가 아닌경우
        for(var i = 0; i < this.dropSwitchList.length; i++){
            var dropSwitch = this.dropSwitchList[i].getComponent("Gobject");

            var dropSwitchId = dropSwitch.GetItemID();


            if(dropSwitchId === index && status === 0){
                var dropItem =  this.FindDropItem(dropSwitchId);
                var itemComp = dropItem.getComponent("Gobject");

                if(dropItem.active === false){
                    this.ShowDropEffect(dropItem.position);
                }
                itemComp.DropItemShow();
                // this.audioManager.getComponent('SoundManager').Play(Env.SFX)
                dropSwitch.Hide();

            }
            else if(dropSwitchId === index && status === 1){
                var dropItem =  this.FindDropItem(dropSwitchId);
                var itemComp = dropItem.getComponent("Gobject");
                // itemComp.Hide();

                itemComp.DropItemHide();

                dropSwitch.Hide();

            }
            else if(dropSwitchId === index && status === 2){
                var dropItem =  this.FindDropItem(dropSwitchId);
                var itemComp = dropItem.getComponent("Gobject");
                // itemComp.Show();
                itemComp.DropItemShow();

                dropSwitch.Show();
            }
            else if(dropSwitchId === index && status === 3){
                var dropItem =  this.FindDropItem(dropSwitchId);
                var itemComp = dropItem.getComponent("Gobject");
                // itemComp.Hide();

                itemComp.DropItemHide();

                dropSwitch.Show();

            }
        }
    },

    /**
     * 드랍아이템 찾는 함수
     * @param id  드롭아이템이랑 매칭되는 스위치 아이디
     * @return {*}  찾은 객체 리턴
     * @constructor
     */
    FindDropItem: function(id){
      for( var i = 0; i <  this.dropItemList.length; i++){
          if(this.dropItemList[i].getComponent("Gobject").GetItemID() === id) return this.dropItemList[i];
      }
    },


    /**
     * 아이템 상태를 관리해주는 함수
     *
     * @param index 아이템 인덱스값을 받음
     * @param status 상태는 스트림데이터로 넘어오는 아이템들 리스트 임.
     * @constructor
     */
    ItemStatusUpdate: function(index, status){


        for(var i = 0; i < this.item.length; i++){
            var itemObject = this.item[i].getComponent("Gobject");

            var itemID = itemObject.GetItemID();
            var itemTag = itemObject.GetItemTag();
            if(itemID === index && status === 0){
                itemObject.Hide();
            }
            else if(itemObject.GetItemID() === index && status === 1){
                itemObject.Show();
            }
        }
    },


    /**
     * Json에서 읽어온 코드 뭉치를 게임에 적용하여 플레이 합니다.
     * 실제 사용되는 함수입니다.
     * @constructor
     */
    OnCodePlay: function(){
        if(this.isPlay) return;
        var self = this;
        this.isPlay = true;

        this.idx = Controller.getInstance().GetProgressId();


        var inter = setInterval(function(){

            console.log("Cur idx ==> "+ self.idx );

            if(Controller.getInstance().isGamePause) {
                self.idx = Controller.getInstance().GetProgressId();
                return;
            }

            if(self.executeCommand(self.idx) === false){
                clearInterval(inter);
                self.isPlay = false;
                Controller.getInstance().SetStatus(false);
            }
            self.idx++;
        }, 1000/60);
    },
});
