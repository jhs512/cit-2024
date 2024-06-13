

const Gobject = require("./Gobject");

var ObjectLaiser = cc.Class({
    extends: Gobject,

    properties: {
        number: 0,
    },

    Init: function(TAG, num){
        this._super(TAG);
        this.number = num;
    },


});
