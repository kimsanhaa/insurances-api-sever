# API 명세

### 기본 URL : http://localhost:8080 

## End Point 

### 보험 계약 저장하기
URL: POST /insurances

새로운 보험 계약을 저장합니다.

요청 body 
```
{
  "productId": 2,
  "period" : 3,
  "collaterals" : [
    3,4
  ]
}
```


