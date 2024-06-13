import { writable } from 'svelte/store';

// subTableRows를 위한 writable 스토어 생성
export const subTableRows = writable([
  { id: 1, grade: "1", class: "A", teacher: "Mr. Smith" }
]);

export const teacherOptions = writable([]); // 서버에서 가져온 담당자 옵션

let nextId = 2; // 다음 행에 사용할 ID

// 행을 추가하는 함수
export function addSubTableRow() {
  subTableRows.update(rows => {
    return [...rows, { id: nextId, grade: "", class: "", teacher: "" }];
  });
  nextId += 1;
}

// 행을 삭제하는 함수
export function deleteSubTableRow(id) {
  subTableRows.update(rows => {
    return rows.filter(row => row.id !== id);
  });
}

// 담당자 값을 업데이트하는 함수
export function updateTeacher(id, newTeacher) {
  subTableRows.update(rows => {
    return rows.map(row => row.id === id ? { ...row, teacher: newTeacher } : row);
  });
}

// 서버에서 DTO 데이터를 가져오는 함수 (예시로 setTimeout 사용)
export function fetchTeacherOptions(memberInput) {
    setTimeout(() => {
        console.log(memberInput);
    const options = memberInput.map(member=>({value: member.id, label: member.name}));
    console.log(options);
    teacherOptions.set(options);
    }, 3000);
    
}