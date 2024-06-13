import dedent from 'dedent';

export const codeArray = [
    {
        'id' : 0,
        'stage' : '스테이지1',
        'code' : 'go()',
        'type' : '이동코드',
        'description' : dedent 
        `
        플레이어 케릭터가 바라보고있는 방향으로 이동하는 명령어입니다.

        일반적인 플레이어 이동 방법입니다.

        조금더 먼거리를 이동히려면 인자를 사용하면 됩니다.
        `,
        'example' : dedent
        `
        한칸 이동
        go()     

        3칸 이동
        go(3)    
        `
        
    },
    {
        'id' : 1,
        'stage' : '스테이지1',
        'code' : 'turnLeft()',
        'type' : '이동코드',
        'description' : dedent 
        `
        플레이어 케릭터의 바라보고 있는 방향을 왼쪽으로 회전 시키는 명령어입니다.
        `,
        'example' : dedent
        `
        왼쪽으로 회전
        turnLeft()
        
        바라보고있는 방향의 뒤편을 보게하기
        turnLeft()
        turnLeft()   
        `
        
    },
    {
        'id' : 2,
        'stage' : '스테이지1',
        'code' : 'turnRight()',
        'type' : '이동코드',
        'description' : dedent 
        `
        플레이어 케릭터의 바라보고 있는 방향을 오른쪽으로 회전 시키는 명령어입니다.
        `,
        'example' : dedent
        `
        오른쪽으로 회전
        turnRight()
        
        바라보고있는 방향의 뒤편을 보게하기
        turnRight()
        turnRight()  
        `
        
    },
    {
        'id' : 3,
        'stage' : '스테이지1',
        'code' : 'range()',
        'type' : '범위지정코드',
        'description' : dedent 
        `
        일정 범위까지 반복하는 명령어 입니다.
        Range는 아래와 같이 구성되어있습니다.
        Range(시작할 숫자 , 끝나는 숫자 , {optional, 반복 될 step})

        시작할 숫자 : 시작될 숫자 단위를 넣습니다. 

        끝나는 숫자 : 반복이 종료될 수를 넣습니다.  

        optional , 반복될 Steop 입니다. 이부분은 비워둬도 문제 없습니다.
        `,
        'example' : dedent
        `
        for 반복문에서  5회 반복하여 케릭터 전진

        for i in range(1, 6):
           go()
        
        1~ 5까지  2씩 증가하여  1, 3 , 5  
         
        range(1, 6, 2):
              
        
        역순으로 출력    5 에서 -> 1 까지
        
        range(5, 1 , -1): 
        `
        
    },
    {
        'id' : 4,
        'stage' : '스테이지1',
        'code' : 'for',
        'type' : '반복코드',
        'description' : dedent 
        `
        동일한 행동을 일정 횟수 반복하는데 사용하는 반복문 입니다.
 
        for i in  range(3):
        
        for 문은 반복 작업을 수행하는데 사용됩니다. 'range(3)'은 0부터 시작하여 2까지의 숫자를 세 번 반복하도록 지정합니다.
        
        따라서 'for i in range(3):'은 0, 1, 2의 숫자를 각각 'i'에 할당하여 세 번의 반복을 의미합니다.
        
        첫 번째 반복에서는 'i'에 0이 할당되고, 두 번째 반복에서는 1이 할당되며, 세 번째 반복에서는 2가 할당됩니다.
        `,
        'example' : dedent
        `
        플레이어를 전진하고 왼쪽으로 이동을 3회 하기

        for i in range(3):
           go()
           tunrLeft()
        `
        
    },
    {
        'id' : 5,
        'stage' : '스테이지1',
        'code' : 'setItem()',
        'type' : '설치코드',
        'description' : dedent 
        `
        로켓 발사체에 엔진, 연료, 추진제등 아이템을  장착하기 위해 사용되는 명령어 입니다.

        바라보고 있는 방향과 장착될 방향이 올바르다면  정상적으로 장착 됩니다.
        `,
        'example' : dedent
        `
        고체추진제 장착하기
        setItem("고체추진제")
        
        액체연료 장착하기
        setItem("액체연료")
        
        
        추가엔진 장착하기
        setItem("추가엔진")
        `
        
    },
    {
        'id' : 6,
        'stage' : '스테이지2',
        'code' : 'and',
        'type' : '연산코드',
        'description' : dedent 
        `
        and 연산자는 모든 조건이 참일 때 전체 표현식이 참이 됩니다.

        만약 A and B 라는 표현식이 있다면, A와 B가 모두 참이어야 전체 표현식이 참이 됩니다.

        하나라도 거짓이면 전체 표현식은 거짓이 됩니다.
        `,
        'example' : dedent
        `
        x = 5
        y = 10

        if  x >= 5 and y < 9:
            # 두 조건 모두 충족하지 않기때문에 
            # 아래코드실행되지않습니다.
            turnLeft()
            go(3)
        elif  x == 5 and y == 10:
            #두 조건 충족하므로 아래 코드 실행됩니다.
            go(2)
            turnLeft()
        `
        
    },
    {
        'id' : 7,
        'stage' : '스테이지2',
        'code' : 'or',
        'type' : '연산코드',
        'description' : dedent 
        `
        or 연산자는 조건 중 하나라도 참이면 전체 표현식이 참이 됩니다.

        만약 A or B 라는 표현식이 있다면, A나 B 중 하나라도 참이면 전체 표현식이 참이 됩니다.

        모든 조건이 거짓이면 전체 표현식은 거짓이 됩니다.
        `,
        'example' : dedent
        `
        x = 5
        y = 10

        if  x >= 5 or y < 9:
            # 앞조건 충족하기에 
            # 코드가 실행됩니다.
            go()
        `
        
    },
    {
        'id' : 8,
        'stage' : '스테이지2',
        'code' : 'variable',
        'type' : '변수코드',
        'description' : dedent 
        `
        변수는 변하는 수로, 특정 값, 문자열, 리스트에 있는 값을 저장할수 있는 임시 저장소 입니다.

        변수의 사용방법은 다음과 같습니다.
        
        healPack = "치료제"
        
        healPack 이라는 변수에 "치료제" 문자열이 저장되어있습니다.
        
        변수의 명은 저장할 값을 유추 할수 있는 변수명으로 지정하는게 좋습니다.
        
        변수는 다음 규칙을 따라야합니다.

        1. 변수 맨압에 숫자로 시작되선 안된다.     
                 12Number   X
        2. 예약어는 사용해선 안된다.            
                 import = 10 X
        `,
        'example' : dedent
        `
        변수에 변수를 할당도 가능합니다.

        item1 = "box"
        item2 = "gold"

        items = item1 + item2
        `
        
    },
    {
        'id' : 9,
        'stage' : '스테이지2',
        'code' : 'print()',
        'type' : '출력코드',
        'description' : dedent 
        `
        게임에서 플레이어가 바라보고 있는 방향에 정보를 출력합니다.
        `,
        'example' : dedent
        `
        하나씩 출력

        print("1")
        print("2")
        
        - for loop를 사용하여 출력
        
        for i in range(1, 3):
           print(i)
        
        - 2개 이상의 문자열을 합쳐 출력할때
        
        charA = "Code"
        charB = "Ython"
        
        print(charA + charB)
        `
        
    },
    {
        'id' : 10,
        'stage' : '스테이지2',
        'code' : 'getInfo()',
        'type' : '획득코드',
        'description' : dedent 
        `
        게임에서 정보를 획득할때 사용되는 명령문 입니다.

        일반적으로 오브젝트에서 정보를 획득하고자 할때 사용되는 명령어 입니다.
        
        문자열 정보를 얻습니다.
        `,
        'example' : dedent
        `
        오브젝트로부터 정보를 얻기

        data = getInfo()
        `
        
    },
    {
        'id' : 11,
        'stage' : '스테이지2',
        'code' : 'getNumber()',
        'type' : '획득코드',
        'description' : dedent 
        `
        게임에서 정보를 획득할때 사용되는 명령문 입니다.

        일반적으로 오브젝트에서 정보를 획득하고자 할때 사용되는 명령어 입니다.
        
        숫자 정보를 얻습니다.
        `,
        'example' : dedent
        `
        오브젝트로부터 정보를 얻기

        num = getNumber()
        `
        
    },
    {
        'id' : 12,
        'stage' : '스테이지2',
        'code' : 'checkFront()',
        'type' : '탐지코드',
        'description' : dedent 
        `
        케릭터가 현재 바라보고 있는 방향 바로 앞에 객체를 탐지 할때 사용됩니다.

        if문과 함께 사용하여 다음과 같이 확인 할수 있습니다.
        `,
        'example' : dedent
        `
        if  checkFront() == "폭탄": 
        # 앞에 폭탄이 있을때 실행하고싶은 코드
        `
        
    },
    {
        'id' : 13,
        'stage' : '스테이지2',
        'code' : 'checkFar()',
        'type' : '탐지코드',
        'description' : dedent 
        `
        일반적으로 Jump() 명령어를 사용하고자할때 점프한 위치에 

        장애물이 있는지 없는지 체크할때 사용됩니다.
        
        if문과 함께 사용하여 다음과 같이 확인 할수 있습니다.
        `,
        'example' : dedent
        `
        if  checkFar() == "폭탄": 
            go()
        else:  
            jump()
        `
        
    },
    {
        'id' : 14,
        'stage' : '스테이지2',
        'code' : 'if',
        'type' : '조건문코드',
        'description' : dedent 
        `
        if 문: if 문은 가장 기본적인 조건문입니다.

        특정 조건이 참(True)이면 해당 조건에 대한 코드 블록을 실행합니다.
       
       if 조건:
           # 조건이 참일 때 실행할 코드
        `,
        'example' : dedent
        `
        아래는 바라보고 있는 곳에 폭탄이 존재하면 왼쪽으로 돌아 이동하는 코드입니다.

        if checkFront() == "폭탄":
            turnLeft()
            go()
        `
        
    },
    {
        'id' : 15,
        'stage' : '스테이지2',
        'code' : 'elif',
        'type' : '조건문코드',
        'description' : dedent 
        `
        elif 문: elif는 "else if"의 줄임말로, 

        이전 조건이 거짓(False)일 때 새로운 조건을 확인하는 데 사용됩니다.

        여러 개의 조건을 순차적으로 검사할 때 유용합니다.
        
        if 조건1:
            # 조건1이 참일 때 실행할 코드
        elif 조건2:
            # 조건1이 거짓이고, 조건2가 참일 때 실행할 코드
        `,
        'example' : dedent
        `
        아래는 폭탄이 존재하면 왼쪽으로 회전하고 이동하고, 

        회복약이라면 전방으로 이동하도록 하는 코드입니다.

        if checkFront() == "폭탄":
            turnLeft()
            go()
        elif checkFront() == "회복약":
            go()
        `
        
    },
    {
        'id' : 16,
        'stage' : '스테이지2',
        'code' : 'else',
        'type' : '조건문코드',
        'description' : dedent 
        `
        else 문: else는 이전의 모든 조건이 거짓일 때 실행됩니다. 즉, 마지막으로 기본 동작을 정의할 때 사용됩니다.

        if 조건1:
            # 조건1이 참일 때 실행할 코드
        elif 조건2:
            # 조건1이 거짓이고, 조건2가 참일 때 실행할 코드
        else:
            # 모든 조건이 거짓일 때 실행할 코드

        이렇게 if, elif, else 문을 사용하여 프로그램은 여러 상황을 고려하여 특정 조건에 맞게 동작하도록 할 수 있습니다.
        `,
        'example' : dedent
        `
        아래는 폭탄이 존재하면 왼쪽으로 회전하고 이동하고, 

        회복약이라면 전방으로 이동하도록 하고,

        모든조건에 맞지 않다면 오른쪽으로 회전하는 코드입니다

        if checkFront() == "폭탄":
            turnLeft()
            go()
        elif checkFront() == "회복약":
            go()
        else : 
            turnRight()
            go()
        `
        
    },
    {
        'id' : 17,
        'stage' : '스테이지2',
        'code' : '> , < , >= , <=',
        'type' : '연산코드',
        'description' : dedent 
        `
        부등호 연산자는 수치형 데이터 또는 다른 비교 가능한 데이터를 비교하는 데 사용됩니다. 

        주로 조건문과 함께 사용되어 조건을 평가하고 분기를 제어하는 데 사용됩니다.
        
        작다(<): 왼쪽 피연산자가 오른쪽 피연산자보다 작은지를 확인합니다.

        크다(>): 왼쪽 피연산자가 오른쪽 피연산자보다 큰지를 확인합니다.

        작거나 같다(<=): 왼쪽 피연산자가 오른쪽 피연산자와 같거나 작은지를 확인합니다.

        크거나 같다(>=): 왼쪽 피연산자가 오른쪽 피연산자와 같거나 큰지를 확인합니다.

        같다  ( == ) : 왼쪽 피연산자와 오른쪽 피연산자가 같은지 확인합니다.
        
        `,
        'example' : dedent
        `
        변수 number가 10이랑 같다면 2칸 이동 되는 분기문입니다.

        number = 10
        
        if number < 10:
           go()
        elif number == 10:
           go(2)
        else number > 10:
          go(3)
        `
        
    },
    {
        'id' : 18,
        'stage' : '스테이지2',
        'code' : '%',
        'type' : '연산코드',
        'description' : dedent 
        `
        모듈러 연산자는 %  를 사용합니다.

        모듈러 연산자는 수를 나누고 남은 나머지를 구하는 연산자이며,

        짝수, 홀수 판별이 가능합니다.
        
        `,
        'example' : dedent
        `
        다음 코드는 2로 나누어 떨어진 값이 0 이면 짝수, 그렇지 않으면 홀수로 출력하는 코드입니다.

        a = [10, 3, 5, 80]

        for i in range(0,5):
            if a[i] % 2 == 0:
                print("짝수")
            else 
                 print("홀수")
        `
        
    },
    {
        'id' : 19,
        'stage' : '스테이지2',
        'code' : 'jump()',
        'type' : '이동코드',
        'description' : dedent 
        `
        케릭터가 현재 바라보고 있는 방향 바로앞을 이동할수 있습니다.

        go()와 다르게 jump는 바로앞 폭탄의 피해를 받지 않고 이동할수 있습니다.
        
        jump를 개별적으로 사용할수 있으나,  if 문과 checkFront() 를 조합하여 사용할수 있습니다.
        `,
        'example' : dedent
        `
        아래코드는 바로앞 폭탄 감지한뒤, 점프로 피하는 코드입니다.

        if checkFront() == "폭탄":
            jump()
        `
        
    },
    {
        'id' : 20,
        'stage' : '스테이지2',
        'code' : 'while()',
        'type' : '반복코드',
        'description' : dedent 
        `
        for 문과 같은 반복문 입니다.

        반복횟수, 조건이 괄호에 들어갑니다.
        `,
        'example' : dedent
        `
        아래는 플레이어의 hp가 30보다 클경우 계속 반복됩니다.

        그리고 앞에 폭탄이 있으면 왼쪽으로 돌아 이동하고.

        전방에  없음 이면  앞으로 이동합니다.

        while getHp() > 30:
            if  checkFront() == "없음": 
                go()
            elif checkfront() =="폭탄":
                turnLeft()
                go()    
        `
        
    },
    {
        'id' : 21,
        'stage' : '스테이지2',
        'code' : 'getHp()',
        'type' : '확인코드',
        'description' : dedent 
        `
        현재 플레이어 케릭터의 hp 정보를 가져오는 명령어입니다.
        `,
        'example' : dedent
        `
        아래는 체력이 20보다 클경우 앞으로 전진하고

        그렇지 않으면 응급 치료제 아이템을 사용하는 코드입니다.

        if getHp()  > 20:
            go()
        else : 
            use("응급치료제")   
        `
        
    },
    {
        'id' : 22,
        'stage' : '스테이지2',
        'code' : 'use()',
        'type' : '사용코드',
        'description' : dedent 
        `
        현재 보유중인 아이템을 사용하는 코드입니다.

        응급치료제, 회복약, 치료키트등 회복 아이템에 사용되는 코드입니다.
        `,
        'example' : dedent
        `
        플레이어 체력이 30보다 낮으면 치료키트를 사용하고,

        그렇지않으면 계속 이동하는 코드입니다.

        while True :

            if getHp() < 30:
                use("치료키트")
            else : 
                go()
 
        `
        
    },
    {
        'id' : 23,
        'stage' : '스테이지2',
        'code' : 'break',
        'type' : '반복코드',
        'description' : dedent 
        `
        반복문을 중단시키는 코드입니다.

        특정 조건이 되었을때 반복을 중단하고자 할때 사용됩니다.
        `,
        'example' : dedent
        `
        다음은 i 를 0 에서 4 까지 출력하는 코드입니다.
        5가 되는순간 반복문이 종료됩니다.

        while True :

            if getHp() < 30:
                use("치료키트")
            else : 
                go()
 
        `
        
    },
    {
        'id' : 24,
        'stage' : '스테이지2',
        'code' : 'continue',
        'type' : '반복코드',
        'description' : dedent 
        `

        반복문(주로 for나 while 루프)에서 사용됩니다. 

        이 문장은 현재 반복을 중지하고 다음 반복으로 넘어가도록 합니다.
        
        continue 문이 실행되면, 반복문의 나머지 부분은 실행되지 않고 다음 반복으로 진행됩니다. 

        이는 반복 중 특정 조건이 충족될 때 일부 코드를 건너뛰고자 할 때 유용합니다.
        `,
        'example' : dedent
        `
        아래는 i 가 짝수라면  아무것도 하지않고, 홀수에  해당되면 앞으로 이동하는 코드 입니다.

        for i in range(1, 6):
            if i%2 ==0:
                continue
            go()
 
        `
        
    },
    {
        'id' : 25,
        'stage' : '스테이지3',
        'code' : 'upper()',
        'type' : '문자열코드',
        'description' : dedent 
        `
        upper() 는 입력받은 문자를 모두 대문자로 치환해줍니다.
        `,
        'example' : dedent
        `
        inputData = getInfo()

        result = inputData.upper()
        `
        
    },
    {
        'id' : 26,
        'stage' : '스테이지3',
        'code' : 'len()',
        'type' : '문자열코드',
        'description' : dedent 
        `
        입력받은 문자의 길이 정보를 리턴해줍니다.

        length 변수에  문자 의 길이 5가 저장되는 코드입니다.
        `,
        'example' : dedent
        `
        ch = "Hello" 
        
        length = len(ch)
        `
        
    },
    {
        'id' : 27,
        'stage' : '스테이지3',
        'code' : 'pass',
        'type' : '반복코드',
        'description' : dedent 
        `
        pass 문은 아무런 동작도 하지 않고, 코드 블록을 건너뛰는 데 사용됩니다.

        보통 제어문이나 함수 등에서 블록을 채우는 것이 요구되지만, 구현은 나중에 할 때나 빈 블록을 일시적으로 만들고자 할 때 사용됩니다. 
        
        예를 들어, 함수를 정의할 때 내용을 나중에 추가할 경우가 있습니다.
        
        짝수가 입력되면 아무것도 하지않습니다. 홀수라면 값을 더합니다.
        `,
        'example' : dedent
        `
        i = 1;
        while True:
           if i %2 == 0 :  
                pass
           i += 1
        `
        
    },
    {
        'id' : 28,
        'stage' : '스테이지3',
        'code' : 'slice',
        'type' : '문자열코드',
        'description' : dedent 
        `
        슬라이싱은 파이썬에서 시퀀스(문자열, 리스트, 튜플 등)의 일부를 추출하는 방법을 말합니다. 

        시퀀스의 일부를 선택하여 새로운 시퀀스를 만들거나 해당 부분의 값을 검색할 때 사용됩니다.
        `,
        'example' : dedent
        `
        일반적으로 슬라이싱은 다음과 같은 형식을 가집니다:

        text = "Hello, World!"
        result = text[1:6]
        print(result)  # 출력: "ello,"


        문자열 "Hello, World!"를 슬라이싱하여 2개씩 건너뛰면서 추출합니다.

        v = text[::2]
        print(v)  # 출력: "Hlo ol!"
        `
        
    },
    {
        'id' : 29,
        'stage' : '스테이지3',
        'code' : 'isupper()',
        'type' : '문자열코드',
        'description' : dedent 
        `
        문자열이 대문자인지 판별합니다.
        `,
        'example' : dedent
        `
        아래코드는 입력받은 문자가 대문자면 go() 그렇지않으면 오른쪽으로 회전합니다
        
        info = getInfo()
        
        for char in info:
            if char.isupper():
               go()
            else:
               turnRight()
        `
        
    },
    {
        'id' : 30,
        'stage' : '스테이지3',
        'code' : 'List',
        'type' : '데이터코드',
        'description' : dedent 
        `
        리스트는 연속적인 값을 저장하고 자 할때 사용되는 데이터 저장공간입니다.
        `,
        'example' : dedent
        `
        리스트의 생성법

        itemList = []
        
        리스트 맨끝에 저장하기
        
        itemList.append("회복약")
        `
        
    },
    {
        'id' : 31,
        'stage' : '스테이지3',
        'code' : 'check()',
        'type' : '확인코드',
        'description' : dedent 
        `
        리스트를 완성하고, 리스트에 저장된 값을 확인할때 사용되는 코드입니다.
        `,
        'example' : dedent
        `
        myList = []
        info = getInfo()
        myList.append(info)

        check(myList)
        `
        
    },
    {
        'id' : 32,
        'stage' : '스테이지3',
        'code' : 'function',
        'type' : '함수코드',
        'description' : dedent 
        `
        함수란 특정 용도의 코드를 한곳에 모아 놓은 기능입니다. 
        
        한 번만 작성해 놓으면 필요할 때 계속 불러 쓸 수 있습니다

        폭탄을 만들고 뒤로 도는 함수입니다.
        `,
        'example' : dedent
        `
        def  setTrapAndGoBack():
            setItem("폭탄")
            turnLeft(2)
            go()
            turnLeft(2)
        
        
        go()
        setTrapAndGoBack()
        `
        
    },
    {
        'id' : 33,
        'stage' : '스테이지3',
        'code' : 'getNumberList()',
        'type' : '획득코드',
        'description' : dedent 
        `
        숫자 리스트를 가져오는 함수입니다.
        `,
        'example' : dedent
        `
        numList = getNumberList()

        for i in numList:
           print(i)
        `
        
    },
    {
        'id' : 34,
        'stage' : '스테이지3',
        'code' : 'getItem()',
        'type' : '획득코드',
        'description' : dedent 
        `
        아이템을 획득하는 함수입니다.

        아이템은 문자열로 주어집니다.
        `,
        'example' : dedent
        `
        myList = []
        item = getItem()

        myList.append(item)
        `
        
    },
    {
        'id' : 35,
        'stage' : '스테이지3',
        'code' : 'getItemList()',
        'type' : '획득코드',
        'description' : dedent 
        `
        아이템리스트를 획득 하는 함수입니다.

        아이템리스트는 문자열 리스트로 주어집니다.
        `,
        'example' : dedent
        `
        itemList = getItemList()
        myList = []

        for item in itemList:
            if(item != "고장난 폭탄") :
                myList.append(item)
        `
    },
    {
        'id' : 36,
        'stage' : '스테이지3',
        'code' : 'attack()',
        'type' : '공격코드',
        'description' : dedent 
        `
        적을 공격하는 명령문 입니다.

        일반적인 공격범우는 8칸 입니다.
        
        Atack("적이름") 으로 사용가능하며
        `,
        'example' : dedent
        `
        go(2)
        attack("해적1")
        turnLeft()
        go()
        attack("해적2")
        `
    },
    {
        'id' : 37,
        'stage' : '스테이지3',
        'code' : 'chargeShot()',
        'type' : '공격코드',
        'description' : dedent 
        `
        보스에게 강력한 공격을 할때 사용됩니다.

        보스가 소환한 폭탄을 에너지원으로 사용합니다.

        일부 폭탄은 chargeShot 의 에너지원으로 쓰기위해 비밀번호를 입력해야합니다.
        `,
        'example' : dedent
        `
        while True:

            if checkfront() == "":
                get("폭탄")
                chargeShot() # chargeShot(비밀번호)

            else: 
                go()
        `
    },
    {
        'id' : 38,
        'stage' : '스테이지3',
        'code' : 'findEnemy()',
        'type' : '탐지코드',
        'description' : dedent 
        `
        근처 적의 이름을 알아내는 명령문입니다.

        일반적으로 적의 이름을 알수 없을때 사용합니다.
        `,
        'example' : dedent
        `
        enemy = findEnemy()

        attack(enemy)
        `
    },
    {
        'id' : 39,
        'stage' : '스테이지3',
        'code' : 'getBomb()',
        'type' : '탐지코드',
        'description' : dedent 
        `
        보스가 던진 폭탄을 습득하고 에너지로 사용하기 위한 명령문입니다.
        `,
        'example' : dedent
        `
        while True:
            if checkFront() == "폭탄":
                get("폭탄")
        `
    },
    {
        'id' : 40,
        'stage' : '스테이지2',
        'code' : 'checkLeft()',
        'type' : '탐지코드',
        'description' : dedent 
        `
        현재 캐릭터의 왼쪽에 있는 오브젝트를 탐지 할때 사용됩니다.

        if문과 함께 사용하여 다음과 같이 확인 할수 있습니다.
        `,
        'example' : dedent
        `
        if  checkLeft() == "폭탄": 
        # 왼편에 폭탄이 있을때 실행하고싶은 코드
        `
        
    },
    {
        'id' : 41,
        'stage' : '스테이지2',
        'code' : 'checkRight()',
        'type' : '탐지코드',
        'description' : dedent 
        `
        현재 캐릭터의 오른쪽에 있는 오브젝트를 탐지 할때 사용됩니다.

        if문과 함께 사용하여 다음과 같이 확인 할수 있습니다.
        `,
        'example' : dedent
        `
        if  checkRight() == "폭탄": 
        # 오른편에 폭탄이 있을때 실행하고싶은 코드
        `
        
    },
]