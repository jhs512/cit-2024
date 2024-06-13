/**
 *
 * SoundManager 클래스는 SFX 최초로 모두 로드하고
 * 필요시 GetSound({Env.TAG}) 이용해 음악정보를 를 가져옵니다.
 * @type {Function}
 */



var SoundManager = cc.Class({
    extends: cc.Component,


    statics: {
        _instance: null,
        getInstance: function () {
            if (!SoundManager._instance) {
                SoundManager._instance = new SoundManager();
                SoundManager._instance.init();
            }
            return SoundManager._instance;
        },
    },


    properties: {
        sound: {
            default: [],
            type: cc.AudioClip,
        },
        isLoadedSFX: false,
    },


    init: function(){
        this._LoadSFX();
    },


    /**
     * 정상적으로 사운드가 로드되었는지 체크하는 함수입니다.
     *
     * @return {*}
     * @constructor
     */
    IsLoadCheck: function(){
        return this.sound[7];
    },

    /**
     * SFX를 출력해주는 함수입니다.
     * @param TAG Env 내부 SFX_로 시작하는 태그들중 하나
     * @constructor
     */
    PlaySfx: function(TAG){

        if(TAG > this.sound.length ) {
            console.log("SFX Load ERROR");
            return;
        }

        if(this.sound[TAG] === null) {
            console.log("SFX NULL ERROR ");
            return;
        }


        console.log("playSFX ==> " ,TAG);

        var self = this;

        var clip = this.sound[TAG];
        cc.audioEngine.play(clip,false, 1, function(){
            console.log("Done");
        }.bind(this));
    },

    /**
     * 내부 함수로 SFX를 모두 loadRes로 로드 합니다.
     * @private
     */
    _LoadSFX: function(){
        if(this.isLoadedSFX) return;

        this.sound = new Array(8).fill(null);

        var self = this;

        cc.loader.loadRes(Env.SFX_DIRECTORY_PATH + Env.SFX_FILENAME_DROP_SWITCH, cc.AudioClip, function (err, clip) {
            if (err) {
                cc.error("Error loading image: " + err);
            } else {
                self.sound[0] = clip;
            }
        });

        cc.loader.loadRes(Env.SFX_DIRECTORY_PATH + Env.SFX_FILENAME_LASER_SWITCH, cc.AudioClip, function (err, clip) {
            if (err) {
                cc.error("Error loading image: " + err);
            } else {
                self.sound[1] = clip;
            }
        });

        cc.loader.loadRes(Env.SFX_DIRECTORY_PATH + Env.SFX_FILENAME_EXPLOSION, cc.AudioClip, function (err, clip) {
            if (err) {
                cc.error("Error loading image: " + err);
            } else {
                self.sound[2] = clip;
            }
        });

        cc.loader.loadRes(Env.SFX_DIRECTORY_PATH + Env.SFX_FILENAME_EARN, cc.AudioClip, function (err, clip) {
            if (err) {
                cc.error("Error loading image: " + err);
            } else {
                self.sound[3] = clip;
            }
        });
        cc.loader.loadRes(Env.SFX_DIRECTORY_PATH + Env.SFX_FILENAME_DROP_ITEM, cc.AudioClip, function (err, clip) {
            if (err) {
                cc.error("Error loading image: " + err);
            } else {
                self.sound[4] = clip;
            }
        });
        cc.loader.loadRes(Env.SFX_DIRECTORY_PATH + Env.SFX_FILENAME_LASER_ON, cc.AudioClip, function (err, clip) {
            if (err) {
                cc.error("Error loading image: " + err);
            } else {
                self.sound[5] = clip;
            }
        });
        cc.loader.loadRes(Env.SFX_DIRECTORY_PATH + Env.SFX_FILENAME_LASER_OFF, cc.AudioClip, function (err, clip) {
            if (err) {
                cc.error("Error loading image: " + err);
            } else {
                self.sound[6] = clip;
            }
        });

        cc.loader.loadRes(Env.SFX_DIRECTORY_PATH + Env.SFX_FILENAME_PARTS_DOCKING, cc.AudioClip, function (err, clip) {
            if (err) {
                cc.error("Error loading image: " + err);
            } else {
                self.sound[7] = clip;
            }
        });


    },

});

