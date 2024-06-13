/**
 * Loader 클래스는 싱글턴 패턴 입니다.
 * Loader 클래스는 Sprite 최초로 모두 로드하고
 * 필요시 GetImage({Env.TAG}) 이용해 이미지를 가져옵니다.
 * @type {Function}
 */
var Loader = cc.Class({
    extends: cc.Component,

    statics: {
        _instance: null,
        getInstance: function () {
            if (!Loader._instance) {
                Loader._instance = new Loader();
                Loader._instance.init();
            }
            return Loader._instance;
        },
    },

    properties: {
        /**
        * 초기화 상태값을 가지고 있습니다.
        * false , 초기화 되지않음.
        * true , 초기화 됨.
        */
        initStatus: false,
        // Sprite 정보를 담고있는 배열입니다.
        spr: [],
        player_IMG : [],

    },

    init: function () {
        console.log("Loaded Sprite");
        this._LoadImage();
    },

    /**
     * 최초 객체가 생성될때 지정된 이미지를 읽어와 저장합니다.
     * 단, 최초 1회만 진행합니다.
     * 일반적으로 Loader 객체가 생성될때 1회 만 동작하도록 하고있습니다.
     * @returns {boolean}
     * @private
     */
    _LoadImage: function () {
        if (this.initStatus) return false;
        this.initStatus = true;

        this.spr = new Array(Env.MAX_LOAD_IMAGE_LENGTH).fill(null);
        this.player_IMG = new Array(4).fill(null);

        var self = this;

        cc.loader.loadRes(Env.DIRECTORY_PATH + Env.FOOD_BOX_FILE_NAME, cc.SpriteFrame, function (err, spr) {
            if (err) {
                cc.error("Error loading image: " + err);
            } else {

                console.log(Env.DIRECTORY_PATH+Env.FOOD_BOX_FILE_NAME);

                self.spr[0] = spr;
            }
        });

        cc.loader.loadRes(Env.DIRECTORY_PATH + Env.N_SWITCH_ON_FILE_NAME, cc.SpriteFrame, function (err, spr) {
            if (err) {
                cc.error("Error loading image: " + err);
            } else {
                self.spr[1] = spr;
            }
        });

        cc.loader.loadRes(Env.DIRECTORY_PATH + Env.N_SWITCH_OFF_FILE_NAME, cc.SpriteFrame, function (err, spr) {
            if (err) {
                cc.error("Error loading image: " + err);
            } else {
                self.spr[2]= spr;
            }
        });

        cc.loader.loadRes(Env.DIRECTORY_PATH + Env.L_SWITCH_ON_FILE_NAME, cc.SpriteFrame, function (err, spr) {
            if (err) {
                cc.error("Error loading image: " + err);
            } else {
                self.spr[3] = spr;
            }
        });

        cc.loader.loadRes(Env.DIRECTORY_PATH + Env.L_SWITCH_OFF_FILE_NAME, cc.SpriteFrame, function (err, spr) {
            if (err) {
                cc.error("Error loading image: " + err);
            } else {
                self.spr[4] = spr;
            }
        });

        cc.loader.loadRes(Env.DIRECTORY_PATH + Env.BATTERY_FILE_NAME, cc.SpriteFrame, function (err, spr) {
            if (err) {
                cc.error("Error loading image: " + err);
            } else {
                self.spr[5] = spr;
            }
        });

        cc.loader.loadRes(Env.DIRECTORY_PATH + Env.BOMB_FILE_NAME, cc.SpriteFrame, function (err, spr) {
            if (err) {
                cc.error("Error loading image: " + err);
            } else {
                self.spr[6] = spr;
            }
        });

        cc.loader.loadRes(Env.DIRECTORY_PATH + Env.ROCKET_EMPTY_FILE_NAME, cc.SpriteFrame, function (err, spr) {
            if (err) {
                cc.error("Error loading image: " + err);
            } else {
                self.spr[7] = spr;
            }
        });

        cc.loader.loadRes(Env.DIRECTORY_PATH + Env.ROCKET_FILLED_FILE_NAME, cc.SpriteFrame, function (err, spr) {
            if (err) {
                cc.error("Error loading image: " + err);
            } else {
                self.spr[8] = spr;
            }
        });

        cc.loader.loadRes(Env.DIRECTORY_PATH + Env.L_START_ON_FILE_NAME, cc.SpriteFrame, function (err, spr) {
            if (err) {
                cc.error("Error loading image: " + err);
            } else {
                self.spr[9] = spr;
            }
        });

        cc.loader.loadRes(Env.DIRECTORY_PATH + Env.L_MIDDLE_FILE_NAME, cc.SpriteFrame, function (err, spr) {
            if (err) {
                cc.error("Error loading image: " + err);
            } else {
                self.spr[10] = spr;
            }
        });

        cc.loader.loadRes(Env.DIRECTORY_PATH + Env.L_END_ON_FILE_NAME, cc.SpriteFrame, function (err, spr) {
            if (err) {
                cc.error("Error loading image: " + err);
            } else {
                self.spr[11] = spr;
            }
        });

        cc.loader.loadRes(Env.DIRECTORY_PATH + Env.L_START_OFF_FILE_NAME, cc.SpriteFrame, function (err, spr) {
            if (err) {
                cc.error("Error loading image: " + err);
            } else {
                self.spr[12] = spr;
            }
        });

        cc.loader.loadRes(Env.DIRECTORY_PATH + Env.L_END_OFF_FILE_NAME, cc.SpriteFrame, function (err, spr) {
            if (err) {
                cc.error("Error loading image: " + err);
            } else {
                self.spr[13] = spr;
            }
        });

        cc.loader.loadRes(Env.DIRECTORY_PATH + Env.GOAL_FILE_NAME, cc.SpriteFrame, function (err, spr) {
            if (err) {
                cc.error("Error loading image: " + err);
            } else {
                self.spr[14] = spr;
            }
        });



        cc.loader.loadRes(Env.DIRECTORY_PATH + Env.VL_START_ON_FILE_NAME, cc.SpriteFrame, function (err, spr) {
            if (err) {
                cc.error("Error loading image: " + err);
            } else {
                self.spr[15] = spr;
            }
        });
        cc.loader.loadRes(Env.DIRECTORY_PATH + Env.VL_MIDDLE_ON_FILE_NAME, cc.SpriteFrame, function (err, spr) {
            if (err) {
                cc.error("Error loading image: " + err);
            } else {
                self.spr[16] = spr;
            }
        });
        cc.loader.loadRes(Env.DIRECTORY_PATH + Env.VL_END_ON_FILE_NAME, cc.SpriteFrame, function (err, spr) {
            if (err) {
                cc.error("Error loading image: " + err);
            } else {
                self.spr[17] = spr;
            }
        });
        cc.loader.loadRes(Env.DIRECTORY_PATH + Env.VL_START_OFF_FILE_NAME, cc.SpriteFrame, function (err, spr) {
            if (err) {
                cc.error("Error loading image: " + err);
            } else {
                self.spr[18] = spr;
            }
        });
        return true;
    },


    /**
     * 메인으로 사용되는 함수입니다.
     * 해당 함수는 매개변수로 ag값 받아
     * Sprite를 리턴해주는 함수입니다.
     * @param tag Env 에 저장되어 있는 Tag값
     * @returns {null|*} 저장되어있는 Sprite 리턴합니다.
     * @constructor
     */
    GetImage: function (tag) {
        // tag에 따라 이미지를 반환하는 로직 구현
        // 예를 들어, tag가 0부터 spr 배열의 길이까지의 인덱스 범위 안에 있다고 가정하고 반환할 수 있음
        if (tag >= 0 && tag < this.spr.length) {
            return this.spr[tag];
        } else {
            cc.warn("MSG :", this.spr.length);
            cc.warn("Invalid tag: " + tag);
            return null;
        }
    },
});