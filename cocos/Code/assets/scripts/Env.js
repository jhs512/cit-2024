
window.Env = {
    /**
     * 맵 기본 사이즈 스케일링
     */
    OFFSET_X : (32*3),
    OFFSET_Y : (32*3),
    //96

    //0,-1

    /**
     * 플레이어 포지션 오프셋.
     */
    PLAYER_RADIO : 32 * 1.5,
    //48



    /**
     * SFX 태그 정보 해당 태그를 이용해 불르도록.
     */
    // 플레이어 에서 진행함
    SFX_FOOTSTEP_L : 0,
    SFX_FOOTSTEP_R : 1,



    /**
     * 음악
     */
    SFX_DIRECTORY_PATH : "./sfx/",

    SFX_FILENAME_DROP_SWITCH : 'drop_switch',
    SFX_FILENAME_LASER_SWITCH : 'laser_switch',
    SFX_FILENAME_EXPLOSION : 'explosion',
    SFX_FILENAME_EARN: 'itemEarn',
    SFX_FILENAME_DROP_ITEM : 'drop_item',
    SFX_FILENAME_LASER_ON : 'laser_on',
    SFX_FILENAME_LASER_OFF : 'laser_off',
    SFX_FILENAME_PARTS_DOCKING: 'parts_done',


    // Audio Manager 에서 불려짐.
    SFX_DROP_SWITCH: 0,
    SFX_LASER_BUTTON: 1,
    SFX_BOMB : 2,
    SFX_EARN_ITEM : 3,
    SFX_DROP_ITEM: 4,
    SFX_LASER_ON : 5,
    SFX_LASER_OFF : 6,
    SFX_PARTS_DOCKING : 7,





    // Loader Singleton 에서 사용되는 전역 넘버
    /**
     * Loader Singletone 객체에 대한 태그
     */
    FOOD: 0,
    NORMAL_SWITCH_ON: 1,
    NORMAL_SWITCH_OFF: 2,
    LASER_SWITCH_ON : 3,
    LASER_SWITCH_OFF:4,
    BATTERY : 5,
    BOMB:6,
    ROCKET_EMPTY : 7,
    ROCKET_FILLED: 8,
    LASER_START_ON:9,
    LASER_MIDDLE_ON:10,
    LASER_END_ON:11,
    LASER_START_OFF:12,
    LASER_END_OFF:13,
    GOAL: 14,

    VLASER_START_ON : 15,
    VLASER_MIDDLE_ON : 16,
    VLASER_END_ON : 17,
    VLASER_START_OFF: 18,
    FLOOR : 19,
    // 최대 사이즈표현
    MAX_LOAD_IMAGE_LENGTH : 20,


    /**
     * Loader class 에서 사용되는 파일명 및 경로
     */
    DIRECTORY_PATH : "./$jin/",

    FOOD_BOX_FILE_NAME : 'foodBox',
    N_SWITCH_ON_FILE_NAME : 'nSwitch_on',
    N_SWITCH_OFF_FILE_NAME : 'nSwitch_off',
    L_SWITCH_ON_FILE_NAME : 'lSwitch_on',
    L_SWITCH_OFF_FILE_NAME : 'lSwitch_off',
    BATTERY_FILE_NAME : "battery",
    BOMB_FILE_NAME : 'bomb',
    ROCKET_EMPTY_FILE_NAME: 'rocket_empty',
    ROCKET_FILLED_FILE_NAME : 'rocket_fill',
    L_START_ON_FILE_NAME : 'lStart_on',
    L_MIDDLE_FILE_NAME : 'lMiddle_on',
    L_END_ON_FILE_NAME : 'lEnd_on',
    L_START_OFF_FILE_NAME : 'lStart_off',
    L_END_OFF_FILE_NAME : 'lEnd_off',
    GOAL_FILE_NAME : 'test-none',

    VL_START_ON_FILE_NAME : 'vlStart_on',
    VL_MIDDLE_ON_FILE_NAME : 'vlMiddle_on',
    VL_END_ON_FILE_NAME : 'vlEnd_on',
    VL_START_OFF_FILE_NAME:'vlStart_off',
    FLOOR_FILE_NAME : 'floor',




    /**
     * 케릭터 방향 정보 (바라보고있는곳)
    */
    DIRECTION_UP: 'up',
    DIRECTION_LEFT: 'left',
    DIRECTION_RIGHT: 'right',
    DIRECTION_DOWN: 'down',

    /**
     * 플레이어 방향
     */
    PLAYER_DEFAULT_UP: 0,
    PLAYER_DEFAULT_DOWN: 1,
    PLAYER_DEFAULT_LEFT:2,
    PLAYER_DEFAULT_RIGHT:3,


    /**
     * 플레이어 애니메이션 정보를 저장하고있음.
     */
    ANIMATION_LEFT: "leftRun",
    ANIMATION_RIGHT: "rightRun",
    ANIMATION_UP: "upRun",
    ANIMATION_DOWN: "downRun",
    ANIMATION_IDLE_LEFT: "left_idle",
    ANIMATION_IDLE_RIGHT: "right_idle",
    ANIMATION_IDLE_UP: "idle_back",
    ANIMATION_IDLE_DOWN: "idle_front",

    ANIMATION_LEFT_HIT : "hit_left",
    ANIMATION_RIGHT_HIT : "hit_right",

};
