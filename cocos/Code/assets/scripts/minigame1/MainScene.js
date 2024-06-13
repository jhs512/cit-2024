// Learn cc.Class:
//  - https://docs.cocos.com/creator/manual/en/scripting/class.html
// Learn Attribute:
//  - https://docs.cocos.com/creator/manual/en/scripting/reference/attributes.html
// Learn life-cycle callbacks:
//  - https://docs.cocos.com/creator/manual/en/scripting/life-cycle-callbacks.html

cc.Class({
    extends: cc.Component,

    properties: {

        sliderBar : cc.ProgressBar,
        targetObject : cc.Node,
        stopBtn: cc.Button,

        isDegreeReverse : false,
        isProgressReverse :false,

        isStopped : false,
        degree: 90,
        progressValue : 0,
    },


    start () {

        this.AddEvent();


        this.DegreeGameStart();
        this.ProgresGameStart();
    },

    onLoad () {

    },

    /**
     * UI Event 추가
     * @constructor
     */
    AddEvent: function(){

        this.sliderBar.interactable = false;
        this.stopBtn.node.on("click",this.StopDegreeLooper,this);
    },

    StopDegreeLooper:function(){
        this.isStoped = !this.isStoped;
    },

    ProgresGameStart: function(){
        var self = this;

        setInterval(()=>{
            if(self.isStoped) return;

            if(self.isProgressReverse){
                self.DecreaseProgress();
            }
            else{
                self.IncreaseProgress();
            }
            self.sliderBar.progress = self.progressValue;

        },0.03);

    },

    IncreaseProgress: function(){
        if(this.progressValue >= 1.0){
            this.isProgressReverse = true;
            return;
        }
        this.progressValue += 0.01;
    },

    DecreaseProgress: function(){
        if(this.progressValue <= 0){
            this.isProgressReverse = false;
            return;
        }
        this.progressValue -= 0.01;
    },

    DegreeGameStart: function(){
        var self = this;
        var degreeInterval = setInterval(()=>{
            if(self.isStoped) return;

            if(self.isDegreeReverse){
                self.DecreaseDegree();
            }
            else{
                self.IncreaseDegree();
            }
            self.targetObject.angle = self.degree;

        },0.03);
    },

    IncreaseDegree: function(){
        if(this.degree >= 90){
            this.isDegreeReverse = true;
            return;
        }
        this.degree += 0.5;
    },

    DecreaseDegree: function(){
        if(this.degree <= -90){
            this.isDegreeReverse = false;
            return;
        }
        this.degree -= 0.5;
    },



});
