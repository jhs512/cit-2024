import ace from 'ace-builds/src-noconflict/ace'; // 
import 'ace-builds/src-noconflict/mode-python'; // 사용할 언어 모드
import 'ace-builds/src-noconflict/theme-monokai'; // 사용할 테마
import 'ace-builds/src-noconflict/ext-language_tools';
import rq from '$lib/rq/rq.svelte';

var Range = ace.require('ace/range').Range;

let editorAutoClose = $state(!!rq.member.player.editorAutoClose);
let editorAutoComplete = $state(!!rq.member.player.editorAutoComplete);

export function setupAceEditor(editorId: string, customCompletions: any[]) {
    const editor = ace.edit(editorId);
    editor.setTheme('ace/theme/monokai');
    editor.session.setMode('ace/mode/python');
    editor.setFontSize("25px");
    editor.setHighlightActiveLine(false);
    editor.setOptions({
        enableBasicAutocompletion: true,
        behavioursEnabled: true,
        enableLiveAutocompletion: true,
        wrap:true,
        hasCssTransforms: true,
        useworker: true
    });

    const langTools = ace.require("ace/ext/language_tools");

    const customCompleter = {
        getCompletions: function(editor: any, session: any, pos: any, prefix: any, callback: any) {
            callback(null, customCompletions);
        }
    };

    langTools.setCompleters([customCompleter]);

    $effect(() => {
        editor.setOptions({
            behavioursEnabled: !!rq.member.player.editorAutoClose,
            enableBasicAutocompletion: !!rq.member.player.editorAutoComplete,
            enableLiveAutocompletion: !!rq.member.player.editorAutoComplete
         }) 
     });


    return editor;
}

