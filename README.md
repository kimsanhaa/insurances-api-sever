# API 명세

### 기본 URL : http://localhost:8080 

## End Point 

### 보험 계약 저장하기
URL: POST /insurances

새로운 보험 계약을 저장

요청 body 
```
header 'Content-Type: application/json' \
{
  "productId": 2,
  "period" : 3,
  "collaterals" : [
    3,4
  ]
}
```
오류 처리

- 요청한 보험 제품의 담보가 아닐때 Http code 400
```
{
    "result": "해당 제품에 맞는 담보가 아닙니다."
}

```
- 요청 기간이 보험 제품의 계약기간보다 클 떄 Http code 400
```
{
    "result": "period 범위를 벗어났습니다."
}

```

### 보험 계약 조회
URL: GET /insurances/{contractId}

보험 계약 상세정보 조회

오류 처리

- contractId에 조회한 값이 없을 때 Http code 400
```
{
    "result": "조회된 결과가 없습니다. 유효한 값을 입력하십시오."
}

```

### 보험 계약 저장하기
URL: PATCH /insurances/{contractId}

가입한 보험계약 수정

요청 body 
```
header 'Content-Type: application/json' \
{
  "productId": 2,
  "contractId" : 3,
  "collateral" :{
    "isUpdate" : "true",
    collateralIds : [3,4]
  },
  "period" : {
    "isUpdate" : "true",
    "period" : 3
  },
  "contract" : {
    "isUpdate" : "true",
    "status" : 3
  }
}
```

오류 처리

- 요청 기간이 보험 제품의 상태가 기간만료 상태인 경우 Http code 400
```
{
    "result": "기간만료된 보험입니다."
}
```
- 요청한 보험 제품의 담보가 아닐때 Http code 400
```
{
    "result": "해당 제품에 맞는 담보가 아닙니다."
}

```
- 요청 기간이 보험 제품의 계약기간보다 클 떄 Http code 400
```
{
    "result": "period 범위를 벗어났습니다."
}
```

### 예상 총 보험료 계산
URL: GET /insurance/quote

보험료 계산

요청 body 
```
header 'Content-Type: application/json' \
{
  "productId": 2,
  "period" : 3,
  "collaterials":[
    3,4
  ]
}
```

오류 처리

- 요청한 보험 제품의 담보가 아닐때 Http code 400
```
{
    "result": "해당 제품에 맞는 담보가 아닙니다."
}

```
- 요청 기간이 보험 제품의 계약기간보다 클 떄 Http code 400
```
{
    "result": "period 범위를 벗어났습니다."
}
```









