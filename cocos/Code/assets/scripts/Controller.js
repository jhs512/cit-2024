/**
 *
 * 웹 페이지와 인터렉션을 받는 클래스 입니다. 싱글턴 클래스입니다.
 * 해당클래스는 Json 정보를 저장하고,
 * Manager 클래스에서 게임을 총괄합니다.
 * Manager 다른클래스에서  해당 클래스를 불러 값을 가져옵ㄴ다.
 *
 * @type {Function}
 */
var Controller = cc.Class({
    statics: {
        // Singleton
        _instance: null,
        getInstance: function () {
            if (!Controller._instance) {
                Controller._instance = new Controller();
                // Controller._instance.init();
            }
            return Controller._instance;
        },
    },

    /**
     *  initJson 최초 초기화 된 정보 json
     *  streamJson 명령문 모든 json 정보
     *  loadIndex 현재 선택된 id 값 index 뜻
     *  gameStatus
     *      - true 업데이트 돌아감
     *      - false 종료 상태
     */
    properties: {
        initJson : null,
        streamJson : null,
        loadIndex : 0,
        gameStatus: false,
        isGamePause: false,

        finalGameLoaded : false,
    },


    /**
     * 최초 맵 초기화 정보를 받는 함수
     * @returns {*} stageJson 값
     */
    getInitStageData: function(){
        return this.initJson.stage;
    },

    /**
     * 최초 플레이어 초기화 정보를 받는 함수
     * @returns {*} playerJson값
     */
    getInitPlayerData: function(){
        return this.initJson.player;
    },


    /**
     * 최초 아이템 포지션 및 설정할때 넘기는 함수.
     * @returns {*}
     */
    getInitOjbectDatas: function(){
        return this.initJson.stage.init_item_list;
    },

    /**
     * 로드된 json정보를 id파라미터를 통해 로드
     * 메인으로 사용되는 함수
     * 최대 명령어 줄이 瑛뺐嚥?-1 값을 리턴 사용처에선 -1이면 명령어 끝을 의미
     * @param id id 값
     * @returns {number|*} id에 해당하는 json 정보모두
     */
    getCommandLine: function(id){


        var data = this.streamJson;

        if(data == null) return -1;

        if( id >= data.data.length ){
            console.log("Out of Range");
            return -1;
        }


        return data.data[id];
    },

    /**
     * 로드된 명령어줄의 길이를 받는 함수
     * @returns {number} Json 현재 길이 리턴
     */
    getCommandLength : function(){
        var data = this.streamJson;

        return data.data.length -1;
    },


    /**
     * 외부에서 불려지고, Json 데이터가 저장되는 함수
     * 다른 곳에선 액세스되선 안됩니다.
     *
     * @param json json 데이터
     * @param isInit  초기화상태
     * @constructor
     */
    ReceiveJson : function(json, isInit = false){
        if(isInit){
            this.initJson = json;

        }
        else{
            this.streamJson = json;
        }

    },

    /**
     * 현재 프로그래스 바 id 값 가져옵니다.
     * @returns {number|*} id 정보
     */
    GetProgressId: function(){
        return this.loadIndex;
    },


    /**
     * 웹에서 접근하고, ID값 passing 해주는 함수
     * @param id id값을 받습니다.
     * @constructor
     */
    SetProgressId: function(id){
        this.loadIndex = id;
    },

    /**
     * 현재 상태값 가져오는 함수
     * 해당 함수로 코드가 종료되었는지, 실행중인지 체크됩니다.
     * 0 ,실행중 아님 상태
     * 1, 실행중 상태
     * @returns {boolean|*} 게임 상태값을 가져옵니다.
     * @constructor
     */
    GetStatus : function(){
        return this.gameStatus;
    },


    /**
     * 웹에서 접근하고 상태값 변경되면 변경 해주는 함수.
     * @constructor
     */
    SetStatus: function(status){
        // 이미 돌고 있으면 막음. 예비.
        this.gameStatus = status;
    },


    /**
     *
     * 옵션정보 넘겨주는 함수
     * @param json Json 옵션 정보
     * @constructor
     */
    SetOption: function(json){

    },

    /**
     * 최초 옵션 정보 받는 Json
     * @param json Json 옵션 정보
     * @constructor
     */
    InitOption: function(json){

    },

    /**
     * 게임 멈추는 함수 (외부에서 불리는 함수)
     * @constructor
     */
    PauseGame: function(){
        this.isGamePause = true;
    },

    /**
     * 게임 이어서 하도록 하는 함수 (외부에서 불리는 함수)
     * @constructor
     */
    ResumeGame : function(){
        this.isGamePause = false;
    },


    FinalLoadDone: function(){
        console.log("로딩완료!");
        this.finalGameLoaded = true;
    },


    /**
     * 최종으로 Cocos 게임 로드 정보 리턴함.
     * @constructor
     */
    GetFinalLoad: function(){
        this.finalGameLoaded;
    },
});

module.exports = Controller;

/**
 * 페이지가 최초 로드될때 Call 되는 Init Json 데이터
 */
window.SendInitData = function(json){
    if(Controller.getInstance().initJson != null) return true;

    Controller.getInstance().ReceiveJson(json , true);

    return false;
};

/**
 * 외부에서 Call 하는 Stream Function
 */
window.SendStreamData = function(json){
    Controller.getInstance().ReceiveJson(json);
    Controller.getInstance().SetProgressId(0);
    Controller.getInstance().SetStatus(true);

};

/**
 * 현재 프로그래스바 id 값 웹에서 넘어오는 함수
 */
window.SetProgressId = function(id){
    Controller.getInstance().SetProgressId(id);
};


/**
 * 웹상 play버튼 클릭시 실행하는 함수
 * status값을 true로 바꿔 update문 돌도록 함.
 * @constructor
 */
window.OnClickPlay = function(){
    Controller.getInstance().SetStatus(true);
};

/**
 * 게임도중 옵션정보가 넘어올때 Json 값.
 * @param json 옵션 json
 * @constructor
 */
window.SetOptions = function(json){
    Controller.getInstance().SetOption(json);
}

/**
 * 최초 실행시 옵션정보 넘겨주는 Json
 * @param json 옵션 Json
 * @constructor
 */
window.InitOption= function(json){
    Controller.getInstance().InitOption(json);
}

/**
 * 외부에서 접근하여 Pasue 상태인경우 게임을 멈추는 함수
 * @constructor
 */
window.ExternalPauseGame = function(){
    Controller.getInstance().PauseGame();
}

/**
 * 외부에서 접근하여 Resume 상태 처리해주는 함수
 * @constructor
 */
window.ExternalResumeGame = function(){
    Controller.getInstance().ResumeGame();
}



/**
 * 외부에서 접근,  최종으로 게임이 로드된 상태임.
 * 해당 함수 불렀을때 ture 값이 나오면 로드가 완료되었음.
 * @constructor
 */
window.IsGameLoaded = function(){
    Controller.getInstance().GetFinalLoad();
}
