# API 명세

기본 URL : http://localhost:8080 

end point 
계약 생성

- url : /contracts
- method: post

검사

- 보험에 가입을 한다
- 요청 기간이 보험 제품의 계약 기간 보다 크면 예외가 발생
- 해당 보험 제품의 담보가 아니면 예외가 발생

계약 정보 조회

- url : /contracts
- method : get
- 가입한 보험계약 상세정보를 를 조회합니다.
- 값이 없으면 예외가 발생합니다.

계약 정보 수정 

- url : /contracts
- method : patch
- 담보 추가 삭제
- 계약기간 변경
- 계약상태 변경
    
    기간만료 상태에서는 예외가 발생합니다.
    
    해당 보험 제품의 담보가 아니면 예외가 발생
    
    제품 계약기간을 넘으면 예외가 발생
    

예상 총 보험료 계산

해당 보험 제품의 담보가 아니면 예외가 발생
