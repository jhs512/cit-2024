
const Controller = require("../Controller");

var Loader = require("../GameLogic/Loader");
var SoundManager = require("../GameLogic/SoundManager");


var Gobject = cc.Class({
    extends: cc.Component,

    properties : {
        itemId : 0,
        itemTAG : 0,
        isStatusChange : false,
        laser_direction : 'h',
    },

    /**
     * 태그값은 Env에 Loader Singleton 변수 선언되어있습니다.
     * 정수값을 받아 최초 이미지 변경
     * 태그값을 받아 객체의 이미지를 변경합니다.
     * @param TAG
     * @constructor
     */
    Init: function(TAG, id){
        this.ChangeSprite(TAG);
        this.itemId = id;
        this.itemTAG = TAG;

        this.isStatusChange = true;

        if(TAG === Env.FOOD ){
            this.node.getChildByName("gliter").getComponent(cc.Animation).play("gliter");
        }
        else if(TAG === Env.GOAL){
            console.log("GOAL HI");
            this.node.getComponent(cc.Animation).play("goal");
        }

    },

    /**
     *  객체의 최초 설정된 태그값을 리턴.
     * @constructor
     */
    GetItemTag: function(){
        return this.itemTAG;
    },

    /**
     * 아이템 아이디값을 리턴
     * @returns {number|*}  아이템 ID
     * @constructor
     */
    GetItemID: function(){
        return this.itemId;
    },



    /**
     * 레이저 객체에서 최초 초기화 하는방식.
     * @param TAG Raser Tag
     * @param isOn 켜져있는지 꺼져있는지 체크.
     * @constructor
     */
    LaserInitial : function(TAG, isOn = false , direction){
        this.laser_direction = direction;

        if(direction === 'h'){
            if(isOn){
                if(TAG === Env.LASER_START_ON){
                    this.ChangeSprite(Env.LASER_START_ON);
                }
                else if(TAG === Env.LASER_MIDDLE_ON){
                    this.node.active = true;
                    this.ChangeSprite(Env.LASER_MIDDLE_ON);

                }
                else if(TAG === Env.LASER_END_ON){
                    this.ChangeSprite(Env.LASER_END_ON);
                }
            }
            else{
                this.isStatusChange = true;
                if(TAG === Env.LASER_START_ON){
                    this.ChangeSprite(Env.LASER_START_OFF);
                }
                else if(TAG === Env.LASER_MIDDLE_ON){
                    this.node.active = false;
                }
                else if(TAG === Env.LASER_END_ON){
                    this.ChangeSprite(Env.LASER_END_OFF);
                }
            }
        }
        else{
            if(isOn){
                if(TAG === Env.LASER_START_ON){
                    this.ChangeSprite(Env.VLASER_START_ON);
                }
                else if(TAG === Env.LASER_MIDDLE_ON){
                    this.node.active = true;
                    this.ChangeSprite(Env.VLASER_MIDDLE_ON);

                }
                else if(TAG === Env.LASER_END_ON){
                    this.node.active = true;
                    this.ChangeSprite(Env.VLASER_END_ON);
                }
            }
            else{
                this.isStatusChange = true;
                if(TAG === Env.LASER_START_ON){
                    this.ChangeSprite(Env.VLASER_START_OFF);
                }
                else if(TAG === Env.LASER_MIDDLE_ON){
                    this.node.active = false;
                }
                else if(TAG === Env.LASER_END_ON){
                    this.node.active = false;
                }
            }
        }



    },

    /**
     * 객체를 숨깁니다.
     * 객체를 보여주려면 .Show()를 사용하세요
     * @constructor
     */
    Hide: function(){
        if(this.isStatusChange === false) return;

        this.isStatusChange = false;
        if(this.itemTAG === Env.LASER_START_ON ){

            if(this.laser_direction === 'h'){
                this.ChangeSprite(Env.LASER_START_OFF);
            }
            else{
                this.ChangeSprite(Env.VLASER_START_OFF);
            }
            //레이저 시작점
        }
        else if(this.itemTAG === Env.LASER_END_ON){
            // 레이저 끝점
            if(this.laser_direction === 'h'){
                this.ChangeSprite(Env.LASER_END_OFF);
            }
            else{
                this.node.active = false;
            }

        }
        else if(this.itemTAG === Env.LASER_SWITCH_ON){
            // 레이저 스위치

            SoundManager.getInstance().PlaySfx(Env.SFX_LASER_BUTTON);

            this.ChangeSprite(Env.LASER_SWITCH_OFF);
        }
        else if(this.itemTAG === Env.ROCKET_EMPTY){
            // 로켓 파츠 처리
            this.ChangeSprite(Env.ROCKET_EMPTY);
        }
        else if(this.itemTAG === Env.NORMAL_SWITCH_ON){
            this.ChangeSprite(Env.NORMAL_SWITCH_OFF);
        }
        else{
            if(this.node.active === false)  return;

            if(this.itemTAG === Env.BOMB){
                SoundManager.getInstance().PlaySfx(Env.SFX_BOMB);
            }
            this.node.active = false;
        }

    },

    /**
     * 객체를 보여줍니다.
     * 객체를 감추려면 .Hide()를 사용하세요
     * @constructor
     */
    Show: function(){
        if(this.isStatusChange) return;
        this.isStatusChange = true;


        if(this.itemTAG === Env.LASER_START_ON ){
            // 레이저 시작점.
            if(this.laser_direction === 'h'){
                this.ChangeSprite(Env.LASER_START_ON);
            }
            else{
                this.ChangeSprite(Env.VLASER_START_ON);
            }

        }
        else if(this.itemTAG === Env.LASER_END_ON){
            // 레이저 끝점
            if(this.laser_direction === 'h'){
                this.ChangeSprite(Env.LASER_END_ON);
            }
            else{
                this.node.active = true;
                this.ChangeSprite(Env.VLASER_MIDDLE_ON);
            }

        }
        else if(this.itemTAG === Env.LASER_SWITCH_ON){
            // 레이저 스위치
            this.ChangeSprite(Env.LASER_SWITCH_ON);
        }
        else if(this.itemTAG === Env.ROCKET_EMPTY){
            // 로켓 파츠 처리
            // this.node.opacity = 255;
            this.ChangeSprite(Env.ROCKET_FILLED);
            SoundManager.getInstance().PlaySfx(Env.SFX_PARTS_DOCKING);

            this.ShowProofEffect();
        }
        else if(this.itemTAG === Env.NORMAL_SWITCH_ON){

            SoundManager.getInstance().PlaySfx(Env.SFX_DROP_SWITCH);
            this.ChangeSprite(Env.NORMAL_SWITCH_ON);
        }
        else{
            if(this.node.active === true) return;
            this.node.active = true;
        }
    },

    /**
     * 드롭 아이템 을 보여주는 함수입니다.
     * @constructor
     */
    DropItemShow: function(){
        if(this.node.active === true) return;
        SoundManager.getInstance().PlaySfx(Env.SFX_DROP_ITEM);

        this.node.active = true;
    },

    /**
     * 드롭아이템을 감추는 함수입니다.
     * @constructor
     */
    DropItemHide: function(isInit = false){
        if(this.node.active === false) return;

        if(!isInit){
            SoundManager.getInstance().PlaySfx(Env.SFX_EARN_ITEM);
        }

        this.node.active = false;
    },


    /**
     * 로켓 파츠 장착시 이펙트를 보여줍니다.
     * @constructor
     */
    ShowProofEffect: function(){
        var proofEffect = this.node.getChildByName("proof");
        proofEffect.active = true

        var animation = proofEffect.getComponent(cc.Animation);

        animation.on('finished', function () {
            proofEffect.active = false;
        }, this);
        animation.play("proof");
    },

    /**
     * tag 값을 받아 sprite를 변경합니다.
     * @param tag Env Tag값을 받습니다..
     * @constructor
     */
    ChangeSprite: function(tag){
        this.node.getComponent(cc.Sprite).spriteFrame = Loader.getInstance().GetImage(tag);
    },

});
