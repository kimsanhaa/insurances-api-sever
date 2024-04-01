# API 명세

### 기본 URL : http://localhost:8080 

## End Point 

### 보험 계약 저장하기
URL: POST /insurances

새로운 보험 계약을 저장합니다.

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

- 요청한 보험 제품의 담보가 아닐때 HttpStatus 400 return 
```
{
    "data": "해당 제품에 맞는 담보가 아닙니다."
}

```





