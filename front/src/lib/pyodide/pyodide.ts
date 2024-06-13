declare global {
    interface Window {
      pyodide: any;
      loadPyodide: any;
    }
  }
  
  export async function loadPyodide() {
    if (!window.pyodide) {
      await new Promise<void>((resolve, reject) => {
        const script = document.createElement('script');
        script.src = '/pyodide.js'; 
        script.onload = () => resolve();
        script.onerror = reject;
        document.body.appendChild(script);
      });
  
      window.pyodide = await window.loadPyodide({
        indexURL: "/pyodide/" 
      });
    }
    return window.pyodide;
  }

let pyodideWorker:any = null;

function getPyodideWorker() {
    if (pyodideWorker === null) {
        pyodideWorker = new Worker('/pyodideWorker.obfuscated.js');

        pyodideWorker.onmessage = (event:any) => {
            if (pyodideWorker.callback) {
                if (event.data.error) {
                    pyodideWorker.callback.reject(event.data.error);
                } else {
                    pyodideWorker.callback.resolve(event.data.result);
                }
            }
        };
        pyodideWorker.onerror = (error:any) => {
            if (pyodideWorker.callback) {
                pyodideWorker.callback.reject(error);
            }
        };
    }

    return pyodideWorker;
}

export async function runPythonCode2(stageData:any, userInput: any) {
    try {
        const result = await runPythonCodeWithTimeout(stageData, userInput, 3000);
        return result; 
    } catch (error) {
        if(stageData !== "" && userInput !== "") {
            return error;
        }
    }
}

function runPythonCodeWithTimeout(stageData:any, userInput:any, timeout:any) {
    return new Promise((resolve, reject) => {
        let worker = getPyodideWorker();
        let timeoutId: any;

        worker.onmessage = (event: any) => {
            clearTimeout(timeoutId);

            resolve(event.data);

            worker.onmessage = null;
            worker.onerror = null;
        };

        worker.onerror = (error:any) => {
            clearTimeout(timeoutId);

            reject(error);

            worker.onmessage = null;
            worker.onerror = null;
        };

        worker.postMessage({ stageData, userInput });

        timeoutId = setTimeout(() => {
            worker.terminate();
            pyodideWorker = null; 
            runPythonCode2("", "");
            reject(new Error('파이썬 코드 실행 시간 초과')); 
        }, timeout);

    });
}

// export async function runPythonCode2(pyodide: any, stageData:any, userInput: any) {
//     try {
//         let result = await pyodide.runPythonAsync(logic1 + stageData + logic2 + "\n" + userInput + logic3);
//         return result;
//     } catch (error) {
//         console.error('Python 코드 실행 중 오류 발생:', error);
//     }
// }
