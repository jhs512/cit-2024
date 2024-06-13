

const Controller = require("Controller");

cc.Class({
    extends: cc.Component,

    properties: {
        // 최초 포지션, 포지션 정보가 저장됩니다.
        // replay 해당 포지션으로 초기화 됩니다.
        initPosition: null,

        // 플레이어가 바라보고 있는 방향입니다.
        direction : Env.DIRECTION_UP,

        // 말풍선 객체입니다.
        bubble: cc.Node,
        // 말풍선 내부에 있는 텍스트 객체입니다.
        bubbleLabel: cc.Label,

        // 메시지가 보여지고있는 상태입니다.
        isShowMessage : false,

        // 죽었음을 표현
        playerIsDead: false,
        playerStatusInfo: 0,


        audioFootStep:{
            default : [],
            type: cc.AudioClip,
        },

        audioStep: 0,
        isPlaySound : false,

        isBombAnimation : false,
    },


    /**
     * 플레이어가 이동합니다.
     * @param pos 이동될 포지션 값
     * @constructor
     */
    Movement: function(pos){
        this.changeSpriteDirection();
        this.node.setPosition(pos);

    },

    PlayFootStep: function(){
        if(this.isPlaySound) return;

        this.isPlaySound = true;
        var self = this;
        setTimeout(()=>{
            self.isPlaySound = false;
        },300);


        var audioClip = this.audioFootStep[this.audioStep]; // 오디오 클립 가져오기
        cc.audioEngine.play(audioClip, false, 1, function () {
            // 오디오가 종료될 때 호출되는 콜백 함수
            // 다음에 재생할 오디오 클립 인덱스 업데이트
            if (this.audioStep === 1)
                this.audioStep = 0;
            else
                this.audioStep = 1;
        }.bind(this));
    },

    /**
     * 현재 방향에 따른 Scale 변경
     * 로테이션 처리
     */
    changeSpriteDirection: function(){

        var animationClip = this.getComponent(cc.Animation)
        // RUN 상태

        var isPlaying = false;
        if(this.playerStatusInfo  < 2){
            this.PlayerInitAnimation();
        }

        if(this.playerStatusInfo === 1){
            switch (this.direction){

                case Env.DIRECTION_UP:
                    var upState = this.getComponent(cc.Animation).getAnimationState(Env.ANIMATION_UP);
                    isPlaying = upState.isPlaying;
                    if (!isPlaying) {
                        animationClip.play(Env.ANIMATION_UP);
                    }
                    break;
                case Env.DIRECTION_DOWN:
                    var downState = this.getComponent(cc.Animation).getAnimationState(Env.ANIMATION_DOWN);
                    isPlaying = downState.isPlaying;
                    if (!isPlaying) {
                        animationClip.play(Env.ANIMATION_DOWN);
                    }

                    break;
                case Env.DIRECTION_RIGHT:
                    var rightState = this.getComponent(cc.Animation).getAnimationState(Env.ANIMATION_RIGHT);
                    isPlaying = rightState.isPlaying;
                    if (!isPlaying) {
                        animationClip.play(Env.ANIMATION_RIGHT);
                    }
                    break;
                case Env.DIRECTION_LEFT:
                    var leftState = this.getComponent(cc.Animation).getAnimationState(Env.ANIMATION_LEFT);
                    isPlaying = leftState.isPlaying;
                    if (!isPlaying) {
                        animationClip.play(Env.ANIMATION_LEFT);
                    }
                    break;
            }
            // 효과음 추가.
            this.PlayFootStep();
        }
        else if(this.playerStatusInfo === 11){
            if(this.isBombAnimation) return;

            console.log("Fc");

            this.isBombAnimation = true;
// 폭탄에 맞음
            var animationClip = this.getComponent(cc.Animation);

            var upState = this.direction === Env.ANIMATION_LEFT ? animationClip.getAnimationState(Env.ANIMATION_LEFT_HIT) : animationClip.getAnimationState(Env.ANIMATION_RIGHT_HIT);
            var isPlaying = upState.isPlaying;
            if (!isPlaying) {
                if (this.direction === Env.ANIMATION_LEFT) {
                    animationClip.play(Env.ANIMATION_LEFT_HIT);
                } else {
                    animationClip.play(Env.ANIMATION_RIGHT_HIT);
                }
            }

            var self = this;
            setTimeout(function(){
                self.isBombAnimation = false;
            }, 1000);

        }
        else{
            if(this.playerStatusInfo !== 9){
                // 방향이 왼쪽을 제외하곤 다 오른쪽 보도록
                if(this.direction === Env.DIRECTION_LEFT){
                    // idle_left 애니메이션 적용 예정
                    this.getComponent(cc.Animation).play(Env.ANIMATION_IDLE_LEFT);
                }
                else if(this.direction === Env.DIRECTION_RIGHT){
                    // idle_right 애니메이션 적용 예정
                    this.getComponent(cc.Animation).play(Env.ANIMATION_IDLE_RIGHT);
                }
                else if(this.direction === Env.DIRECTION_UP){
                    this.getComponent(cc.Animation).play(Env.ANIMATION_IDLE_UP);

                }
                else if(this.direction === Env.DIRECTION_DOWN){
                    this.getComponent(cc.Animation).play(Env.ANIMATION_IDLE_DOWN);
                }
            }
        }
    },


    /**
     * 플레이어 객체가 생성되면 최초로 해당 함수를 불러 초기화 해야합니다.
     * @param v2  최초 포지션 인덱스를 가지고 옵니다.
     * @param DIR  최초 플레이어가 바라보고 있는 방향
     */
    Init: function(v2 , DIR){
        this.initPosition = v2;
        this.node.setPosition(v2);
        this._HideBubble();
        this.PlayerInitAnimation();

        this.node.zIndex = cc.macro.MAX_ZINDEX - 1;

        var defaultDIR = 0;
        switch (DIR){
            case Env.DIRECTION_LEFT: defaultDIR = Env.PLAYER_DEFAULT_LEFT; break;
            case Env.DIRECTION_RIGHT: defaultDIR = Env.PLAYER_DEFAULT_RIGHT; break;
            case Env.DIRECTION_UP: defaultDIR = Env.PLAYER_DEFAULT_UP; break;
            case Env.DIRECTION_DOWN: defaultDIR = Env.PLAYER_DEFAULT_DOWN; break;
        }


        if(defaultDIR === Env.PLAYER_DEFAULT_LEFT){
            this.getComponent(cc.Animation).play(Env.ANIMATION_IDLE_LEFT);
        }
        else if(defaultDIR === Env.PLAYER_DEFAULT_UP){
            // 위로 보고있을때
            this.getComponent(cc.Animation).play(Env.ANIMATION_IDLE_UP);
        }
        else if(defaultDIR === Env.PLAYER_DEFAULT_DOWN){
            // 아래보고있을때.
            this.getComponent(cc.Animation).play(Env.ANIMATION_IDLE_DOWN);
        }
        else if(defaultDIR === Env.PLAYER_DEFAULT_RIGHT){
            this.getComponent(cc.Animation).play(Env.ANIMATION_IDLE_RIGHT);
        }
    },

    /**
     * 플레이어 정보를 초기화 하는 함수입니다.
     */
    playerInit : function(){
        this.node.setPosition(this.initPosition);
        this.direction = Env.DIRECTION_RIGHT;
        this.changeSpriteDirection();
    },

    /**
     * 플레이어 바라보고 있는 방향을 설정합니다.
     * @param dir 플레이어가 바라보는 방향정보
     */
    setDirection : function(dir){
        this.direction = dir;
    },

    /**
     * 플레이어 상태값을 입력받아
     * 말풍선, 죽음, 플레이어상태 표현 출력
     * @param status
     */
    setPlayerStatus: function(status){

        this.playerStatusInfo = status;
        if(this.playerStatusInfo < 2 ) return;


        switch (status) {
            case 9:
                // 플레이어 죽음.
                this.PlayerDealAnimation();
                break;
            case 10:
                // 해당방향으로 이동하지못함.
                this.ShowMessage("이 방향으로 이동할수 없어!");
                break;
//             case 11:
//
//                 if(this.isBombAnimation) return;
//
//                 console.log("Fc");
//
//                 this.isBombAnimation = true;
// // 폭탄에 맞음
//                 var animationClip = this.getComponent(cc.Animation);
//
//                 var upState = this.direction === Env.ANIMATION_LEFT ? animationClip.getAnimationState(Env.ANIMATION_LEFT_HIT) : animationClip.getAnimationState(Env.ANIMATION_RIGHT_HIT);
//                 var isPlaying = upState.isPlaying;
//                 if (!isPlaying) {
//                     if (this.direction === Env.ANIMATION_LEFT) {
//                         animationClip.play(Env.ANIMATION_LEFT_HIT);
//                     } else {
//                         animationClip.play(Env.ANIMATION_RIGHT_HIT);
//                     }
//                 }
//
//                 var self = this;
//                 setTimeout(function(){
//                     self.isBombAnimation = false;
//                 }, 1000);
//
//
//                 break;

            case 13:
                // Set 명령어 시도중 방향이 다를경우
                this.ShowMessage("이곳에서 할수 없는 명령어야!");
                break;
            case 14:
                this.ShowMessage("문자열이 잘못 입력되었어.");
                break;

            case 15:
                this.ShowMessage("고체추진제 장착!");
                // 고체추진제
                break;
            case 16:
                // 액체연료
                this.ShowMessage("액체연료 장착!");
                break;
            case 17:
                // 추가엔진
                this.ShowMessage("추가 엔진 장착!");
                break;
        }

    },

    /**
     * 메시지를 띄워줍니다.
     * ex) 플레이어가 이동불가, 특별한 상태 메시지를 띄워줍니다.
     * @param label 표기될 텍스트
     */
    ShowMessage: function(label){
        if(this.isShowMessage) return;


        this.bubbleLabel.string =  label;
        this._ShowBubble();

        var self = this;
        setTimeout(function(){
            self._HideBubble();
        }, 1000); // 2000 밀리초 = 2초
    },

    /**
     * 메시지를 감춥니다.
     * @private
     */
    _HideBubble: function(){
        this.isShowMessage = false;
        this.bubble.active = false;
    },

    /**
     * 메시지를 보여줍니다.
     * @private
     */
    _ShowBubble: function(){
        this.isShowMessage = true;
        this.bubble.active = true;
    },

    PlayerInitAnimation: function(){
        if(this.playerIsDead === false) return;
        this.playerIsDead = false;
        this.node.opacity = 255;

    },

    PlayerDealAnimation: function(){
        if(this.playerIsDead) return;
        this.playerIsDead = true;

        var self = this;
        var animationClip = this.getComponent(cc.Animation);
        // if (this.direction === Env.ANIMATION_LEFT) {
        //     animationClip.play(Env.ANIMATION_LEFT_HIT);
        // } else {
        //     animationClip.play(Env.ANIMATION_RIGHT_HIT);
        // }
        var pInterval = setInterval(function(e){
            if(self.node.opacity < 1){
                clearInterval(pInterval);
            }
            self.node.opacity -= 10;
        }, 20);
    },



});
