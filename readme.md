# COUPONS MANAGEMENT SYSTEM

| Technology Used | DataBase | Testing FrameWork
|----------------|-----------|----------------|
| ***Java,SpringBoot*** | ***h2***      | ***Junit,Mockito***

- [x] Create a new coupon.
- [x] Retrieve all coupons.
- [x] Retrieve a specific coupon by its ID.
- [x] Update a specific coupon by its ID.
- [x] Delete a specific coupon by its ID.
- [x] Fetch all applicable coupons for a given cart and calculate the total discount that will be applied by each coupon.
- [ ] Apply a specific coupon to the cart and return the updated cart with discounted prices for each item(yet to be done).
- [x] Implement unit tests(I have implemented the junit and written 1 testcases in future I can add more test case coverage).
- [x] Add expiration dates for coupons.(Provided the provision for it In code I am not using it.)

### Assumptions:
- I am assuming we will have ui page from where we will try to add the new coupons when clicked on save it will create new coupons.
- In ui based on catagory type of coupon some fileds will be hide un-hide ex: if the user is adding Product-wise coupon then wwe use productCodes attribute and bxgy we use bxgyproducts.
- I have designed the system in such a way that if you add same coupon code again as new coupon it will not except, we can edit the existing one.
- While selecting the cart I have assumed there is different products table which have info of all product only we us this db to store the cart items and how much is its discount. 

### Limitations
- I need to implement Apply a specific coupon to the cart and return the updated cart with discounted prices for each item.
- I have validated happy flow scenarios but unexpedted scenerios can happen I need to work on it.
- Need to strengthen the validation based on business Scenerios.
- Now when user try to fetch all coupons it will show all coupons, even the expired one.

  
**Implemented Cases**

**Assumption**:I have assumed that main product items details tabel is already present user is selecting the product from there and adding it in cart that product I am considering it.  

- # **POST /coupons** 
### Create a new coupon.

**Request Body**
```json
[
    {
        "couponCode": "ESYFLY20",
        "discountPercentage": 20.00,
        "thresholdAmount": 3000.00,
        "couponType": "Cart_wise",
        "expiryDate": "2025-01-21T05:47:08.644",
        "repetitionLimit": "2",
        "bxgyProducts": null,
        "productCodes": null
    },
    {
        "couponCode": "AAF50",
        "discountPercentage": 50.00,
        "thresholdAmount": 10000.00,
        "couponType": "Cart_wise",
        "expiryDate": "2025-01-21T05:47:08.644",
        "repetitionLimit": "2",
        "bxgyProducts": null,
        "productCodes": null
    },
    {
        "couponCode": "P5",
        "discountPercentage": 5.00,
        "thresholdAmount": 500.00,
        "couponType": "Product_wise",
        "expiryDate": "2025-03-21T05:47:08.644",
        "repetitionLimit": "2",
        "bxgyProducts": null,
        "productCodes": [
            {
                "productCode": "01"
            },
            {
                "productCode": "2"
            }
        ]
    },
    {
        "couponCode": "P6",
        "discountPercentage": 5.00,
        "thresholdAmount": 500.00,
        "couponType": "BxGy",
        "expiryDate": "2025-03-21T05:47:08.644",
        "repetitionLimit": "2",
        "bxgyProducts": {
            "productCode": [
                "01",
                "02",
                "03"
            ],
            "giveFreeProductCode": [
                "04",
                "05",
                "06"
            ]
    },
        "productCodes": null
    }
]
```
---
**Response Body**
If every thing is good the coupons get saved in h2 database and return same object with extra id is assigned to couponId.
```json
[
    {
        "createdDate": "2025-01-04T22:43:53.424581",
        "updatedDate": "2025-01-04T22:43:53.424581",
        "couponId": 1,
        "couponCode": "ESYFLY20",
        "discountPercentage": 20.00,
        "thresholdAmount": 3000.00,
        "couponType": "Cart_wise",
        "expiryDate": "2025-01-21T05:47:08.644",
        "repetitionLimit": 2,
        "bxgyProducts": null,
        "productCodes": null
    },
    {
        "createdDate": "2025-01-04T22:43:53.463586",
        "updatedDate": "2025-01-04T22:43:53.463586",
        "couponId": 2,
        "couponCode": "AAF50",
        "discountPercentage": 50.00,
        "thresholdAmount": 10000.00,
        "couponType": "Cart_wise",
        "expiryDate": "2025-01-21T05:47:08.644",
        "repetitionLimit": 2,
        "bxgyProducts": null,
        "productCodes": null
    },
    {
        "createdDate": "2025-01-04T22:43:53.464582",
        "updatedDate": "2025-01-04T22:43:53.502583",
        "couponId": 3,
        "couponCode": "P5",
        "discountPercentage": 5.00,
        "thresholdAmount": 500.00,
        "couponType": "Product_wise",
        "expiryDate": "2025-03-21T05:47:08.644",
        "repetitionLimit": 2,
        "bxgyProducts": null,
        "productCodes": [
            {
                "productCode": "01"
            },
            {
                "productCode": "2"
            }
        ]
    },
    {
        "createdDate": "2025-01-04T22:43:53.472579",
        "updatedDate": "2025-01-04T22:43:53.50458",
        "couponId": 4,
        "couponCode": "P6",
        "discountPercentage": 5.00,
        "thresholdAmount": 500.00,
        "couponType": "BxGy",
        "expiryDate": "2025-03-21T05:47:08.644",
        "repetitionLimit": 2,
        "bxgyProducts": {
            "productCode": [
                "01",
                "02",
                "03"
            ],
            "giveFreeProductCode": [
                "04",
                "05",
                "06"
            ]
        },
        "productCodes": null
    }
```
---
some extra use cases I have uploaded the images

---

- # **GET /coupons: Retrieve all coupons**
 **URL**:http://localhost:8081/coupons
 **Response Body**
```json
 [
    {
        "createdDate": "2025-01-04T22:43:53.424581",
        "updatedDate": "2025-01-04T22:43:53.424581",
        "couponId": 1,
        "couponCode": "ESYFLY20",
        "discountPercentage": 20.00,
        "thresholdAmount": 3000.00,
        "couponType": "Cart_wise",
        "expiryDate": "2025-01-21T05:47:08.644",
        "repetitionLimit": 2,
        "bxgyProducts": null,
        "productCodes": null
    },
    {
        "createdDate": "2025-01-04T22:43:53.463586",
        "updatedDate": "2025-01-04T22:43:53.463586",
        "couponId": 2,
        "couponCode": "AAF50",
        "discountPercentage": 50.00,
        "thresholdAmount": 10000.00,
        "couponType": "Cart_wise",
        "expiryDate": "2025-01-21T05:47:08.644",
        "repetitionLimit": 2,
        "bxgyProducts": null,
        "productCodes": null
    },
    {
        "createdDate": "2025-01-04T22:43:53.464582",
        "updatedDate": "2025-01-04T22:43:53.502583",
        "couponId": 3,
        "couponCode": "P5",
        "discountPercentage": 5.00,
        "thresholdAmount": 500.00,
        "couponType": "Product_wise",
        "expiryDate": "2025-03-21T05:47:08.644",
        "repetitionLimit": 2,
        "bxgyProducts": null,
        "productCodes": [
            {
                "productCode": "01"
            },
            {
                "productCode": "2"
            }
        ]
    },
    {
        "createdDate": "2025-01-04T22:43:53.472579",
        "updatedDate": "2025-01-04T22:43:53.50458",
        "couponId": 4,
        "couponCode": "P6",
        "discountPercentage": 5.00,
        "thresholdAmount": 500.00,
        "couponType": "BxGy",
        "expiryDate": "2025-03-21T05:47:08.644",
        "repetitionLimit": 2,
        "bxgyProducts": {
            "productCode": [
                "01",
                "02",
                "03"
            ],
            "giveFreeProductCode": [
                "04",
                "05",
                "06"
            ]
        },
        "productCodes": null
    },
    {
        "createdDate": "2025-01-04T23:17:46.683146",
        "updatedDate": "2025-01-04T23:17:46.684256",
        "couponId": 6,
        "couponCode": "ESYFLY40",
        "discountPercentage": 20.00,
        "thresholdAmount": 3000.00,
        "couponType": "Cart_wise",
        "expiryDate": "2025-01-21T05:47:08.644",
        "repetitionLimit": 2,
        "bxgyProducts": null,
        "productCodes": null
    },
    {
        "createdDate": "2025-01-04T23:25:57.818248",
        "updatedDate": "2025-01-04T23:25:57.824253",
        "couponId": 7,
        "couponCode": "CB30",
        "discountPercentage": 30.00,
        "thresholdAmount": 500.00,
        "couponType": "Product_wise",
        "expiryDate": "2025-03-21T05:47:08.644",
        "repetitionLimit": 2,
        "bxgyProducts": null,
        "productCodes": [
            {
                "productCode": "01"
            },
            {
                "productCode": "02"
            }
        ]
    },
    {
        "createdDate": "2025-01-04T23:28:52.384666",
        "updatedDate": "2025-01-04T23:28:52.394671",
        "couponId": 8,
        "couponCode": "BUYGET1",
        "discountPercentage": 0.00,
        "thresholdAmount": 500.00,
        "couponType": "BxGy",
        "expiryDate": "2025-03-21T05:47:08.644",
        "repetitionLimit": 2,
        "bxgyProducts": {
            "productCode": [
                "01",
                "02",
                "03"
            ],
            "giveFreeProductCode": [
                "04",
                "05",
                "06"
            ]
        },
        "productCodes": null
    }
]
```
---

- # **GET /coupons/{id}: Retrieve a specific coupon by its ID.**
  **URL**:http://localhost:8081/coupons/8
  ```json
  **ResponseBody**:

  [
   {
        "couponCode": "BUYGET1",
        "discountPercentage": 0.00,
        "thresholdAmount": 500.00,
        "couponType": "BxGy",
        "expiryDate": "2025-03-21T05:47:08.644",
        "repetitionLimit": "2",
        "bxgyProducts": {
            "productCode": [
                "01",
                "02",
                "03"
            ],
            "giveFreeProductCode": [
                "04",
                "05",
                "06"
            ]
    },
        "productCodes": null
    }
]
```
---
if invalid couponId is given 
**URL**:http://localhost:8081/coupons/30

**ResponseBody**:
```json
Invalid Coupon Id
```
---

- # **PUT /coupons/{id}: Update a specific coupon by its ID**
  ## update the existing coupon

  **URL**:http://localhost:8081/coupons/4
  **RequestBody**:I am only updating discountCode P6 to A430 ,discountPercentage from 5 to 10%
   "couponCode": "A430",
    "discountPercentage": 10.00 remaining same body
---
```json
  previous data in DB:
  {
    "createdDate": "2025-01-04T22:43:53.472579",
    "updatedDate": "2025-01-04T22:43:53.50458",
    "couponId": 4,
    "couponCode": "P6",
    "discountPercentage": 5.00,
    "thresholdAmount": 500.00,
    "couponType": "BxGy",
    "expiryDate": "2025-03-21T05:47:08.644",
    "repetitionLimit": 2,
    "bxgyProducts": {
        "productCode": [
            "01",
            "02",
            "03"
        ],
        "giveFreeProductCode": [
            "04",
            "05",
            "06"
        ]
    },
    "productCodes": null
}
```
---
After updating:
```json
[
    {
        "createdDate": null,
        "updatedDate": "2025-01-05T00:27:58.537375",
        "couponId": 4,
        "couponCode": "A430",
        "discountPercentage": 10.00,
        "thresholdAmount": 500.00,
        "couponType": "BxGy",
        "expiryDate": "2025-03-21T05:47:08.644",
        "repetitionLimit": 2,
        "bxgyProducts": {
            "productCode": [
                "01",
                "02",
                "03"
            ],
            "giveFreeProductCode": [
                "04",
                "05",
                "06"
            ]
        },
        "productCodes": null
    }
]

This did not create idepotency issue i.e Duplicate records were not created.

---
```
- # **DELETE /coupons/{id}: Delete a specific coupon by its ID.**
  ### Deleting existing coupon

  **URL**:http://localhost:8081/coupons/4
```
  **ResponseBody**:Successfully Deleted The Coupon
```
  ---


- # **POST /applicable-coupons: Fetch all applicable coupons for a given cart and calculate the total discount that will be applied by each coupon.**
  ### Based on cart item send Eligible Coupons

---
All Coupon Request in DB:
```json
[
    {
        "createdDate": "2025-01-04T22:43:53.424581",
        "updatedDate": "2025-01-04T22:43:53.424581",
        "couponId": 1,
        "couponCode": "ESYFLY20",
        "discountPercentage": 20.00,
        "thresholdAmount": 3000.00,
        "couponType": "Cart_wise",
        "expiryDate": "2025-01-21T05:47:08.644",
        "repetitionLimit": 2,
        "bxgyProducts": null,
        "productCodes": null
    },
    {
        "createdDate": "2025-01-04T22:43:53.463586",
        "updatedDate": "2025-01-04T22:43:53.463586",
        "couponId": 2,
        "couponCode": "AAF50",
        "discountPercentage": 50.00,
        "thresholdAmount": 10000.00,
        "couponType": "Cart_wise",
        "expiryDate": "2025-01-21T05:47:08.644",
        "repetitionLimit": 2,
        "bxgyProducts": null,
        "productCodes": null
    },
    {
        "createdDate": "2025-01-04T22:43:53.464582",
        "updatedDate": "2025-01-04T22:43:53.502583",
        "couponId": 3,
        "couponCode": "P5",
        "discountPercentage": 5.00,
        "thresholdAmount": 500.00,
        "couponType": "Product_wise",
        "expiryDate": "2025-03-21T05:47:08.644",
        "repetitionLimit": 2,
        "bxgyProducts": null,
        "productCodes": [
            {
                "productCode": "01"
            },
            {
                "productCode": "2"
            }
        ]
    },
    {
        "createdDate": null,
        "updatedDate": "2025-01-05T00:27:58.537375",
        "couponId": 4,
        "couponCode": "A430",
        "discountPercentage": 10.00,
        "thresholdAmount": 500.00,
        "couponType": "BxGy",
        "expiryDate": "2025-03-21T05:47:08.644",
        "repetitionLimit": 2,
        "bxgyProducts": {
            "productCode": [
                "01",
                "02",
                "03"
            ],
            "giveFreeProductCode": [
                "04",
                "05",
                "06"
            ]
        },
        "productCodes": null
    },
    {
        "createdDate": "2025-01-04T23:25:57.818248",
        "updatedDate": "2025-01-04T23:25:57.824253",
        "couponId": 7,
        "couponCode": "CB30",
        "discountPercentage": 30.00,
        "thresholdAmount": 500.00,
        "couponType": "Product_wise",
        "expiryDate": "2025-03-21T05:47:08.644",
        "repetitionLimit": 2,
        "bxgyProducts": null,
        "productCodes": [
            {
                "productCode": "02"
            },
            {
                "productCode": "01"
            }
        ]
    },
    {
        "createdDate": "2025-01-04T23:28:52.384666",
        "updatedDate": "2025-01-04T23:28:52.394671",
        "couponId": 8,
        "couponCode": "BUYGET1",
        "discountPercentage": 0.00,
        "thresholdAmount": 500.00,
        "couponType": "BxGy",
        "expiryDate": "2025-03-21T05:47:08.644",
        "repetitionLimit": 2,
        "bxgyProducts": {
            "productCode": [
                "01",
                "02",
                "03"
            ],
            "giveFreeProductCode": [
                "04",
                "05",
                "06"
            ]
        },
        "productCodes": null
    }
]
```
---
```json
  **RequestBody1**:
  {
    "totalPrice":2600,
    "products":[{
        "productCode":"02",
        "productName":"Silver Plates",
        "productPrice":900,
        "productType":"HOUSEITEM",
        "productQuantity":1

    },{
        "productCode":"01",
        "productName":"Jar",
        "productPrice":200,
        "productType":"HOUSEITEM",
        "productQuantity":1

    },{
        "productCode":"03",
        "productName":"water bottle",
        "productPrice":900,
        "productType":"HOUSEITEM",
        "productQuantity":1

    },{
        "productCode":"04",
        "productName":"oneplus charger",
        "productPrice":600,
        "productType":"ELECTRONIC",
        "productQuantity":1

    }]
}
```
---
  **Eligible coupons**
  **Response Body**:
```json
  [
    {
        "couponId": 7,
        "couponCode": "CB30",
        "discountPercentage": "30.00",
        "couponType": "Product_wise"
    },
    {
        "couponId": 4,
        "couponCode": "A430",
        "discountPercentage": "10.00",
        "couponType": "BxGy"
    },
    {
        "couponId": 7,
        "couponCode": "CB30",
        "discountPercentage": "30.00",
        "couponType": "Product_wise"
    },
    {
        "couponId": 3,
        "couponCode": "P5",
        "discountPercentage": "5.00",
        "couponType": "Product_wise"
    },
    {
        "couponId": 8,
        "couponCode": "BUYGET1",
        "discountPercentage": "0.00",
        "couponType": "BxGy"
    }
]

**It contains all 3 catagory Scenerios as of now you may able to see repetative coupon code code because the 2 items or product are eligible for cart discount that I will fix in further commit**
```
  ---

  Scenerio 2:
  when amount is of cart is high but no product are eligible for discount:
  ```json
Request Body:
{
    "totalPrice":59000,
    "products":[{
        "productCode":"035",
        "productName":"Mobile Cover",
        "productPrice":900,
        "productType":"HOUSEITEM",
        "productQuantity":1

    },{
        "productCode":"013",
        "productName":"IPHONE-6",
        "productPrice":50000,
        "productType":"ELECTRONIC",
        "productQuantity":1

    }]
}

 ```
- Based on this request in db if you see above results in Product-wise catagory type we have 2 coupon code i.e=CB30,P5 in that non of the product codes are matching with the current payload request so no
  product coupon is applicable
- if we consider BxGy catagory  2 coupons are present but in non of the request payload productsCodes are present in bxgyProducts so this is also not eligible
- In if we go through cart wise price its total-price is  59000 i.e in DB we have 2 cart-wise coupons ESYFLY20,AAF50 whose thresold amount limit is 3000,10000 both are valid so this two will be returned as response.
 ---
 ```json
 **Response Body**
  [
    {
        "couponId": 2,
        "couponCode": "AAF50",
        "discountPercentage": "50.00",
        "couponType": "Cart_wise"
    },
    {
        "couponId": 1,
        "couponCode": "ESYFLY20",
        "discountPercentage": "20.00",
        "couponType": "Cart_wise"
    }
]
```
---
Scenerio 3: when amount of cart is low but no product are eligible for discount:
```json
**RequestBody**
same request as above I will change the amount
{
    "totalPrice":1400,
    "products":[{
        "productCode":"035",
        "productName":"Mobile Cover",
        "productPrice":900,
        "productType":"HOUSEITEM",
        "productQuantity":1

    },{
        "productCode":"013",
        "productName":"IPHONE-6",
        "productPrice":500,
        "productType":"ELECTRONIC",
        "productQuantity":1
    }]
}
```
- nocoupons are eligible Because cart amount is less and not product code matches the coupons productcode
  ```json
  **ResponseBody**
  No Coupons Are Eligible to Apply
  ```
  ---
Scenerio 4: when amount of cart is low but  product-wise coupons are eligible for discount:
```json
**Response Body**
{
    "totalPrice":590,
    "products":[{
        "productCode":"02",
        "productName":"books",
        "productPrice":90,
        "productType":"HOUSEITEM",
        "productQuantity":1

    },{
        "productCode":"013",
        "productName":"IPHONE-6",
        "productPrice":500,
        "productType":"ELECTRONIC",
        "productQuantity":1

    }]
}
  
```
- In cart there is one item book with product code 02 which is availiable for Discount
```json
**ResponseBody**
  
  [
    {
        "couponId": 7,
        "couponCode": "CB30",
        "discountPercentage": "30.00",
        "couponType": "Product_wise"
    }
]
```
---
Scenerio 5: when cart has bgxg items which is avaliable for Discount:
```json
**ResponseBody**
{
    "totalPrice":100,
    "products":[{
        "productCode":"02",
        "productName":"books",
        "productPrice":90,
        "productType":"HOUSEITEM",
        "productQuantity":1

    },{
        "productCode":"01",
        "productName":"pencile",
        "productPrice":5,
        "productType":"ELECTRONIC",
        "productQuantity":1

    },{
        "productCode":"04",
        "productName":"sharpner",
        "productPrice":5,
        "productType":"ELECTRONIC",
        "productQuantity":1

    }]
}

```
- As it contain 2 buy product from coupon and one free product he is eligible for it

```json
**ResponseBody**
[
    {
        "couponId": 7,
        "couponCode": "CB30",
        "discountPercentage": "30.00",
        "couponType": "Product_wise"
    },
    {
        "couponId": 8,
        "couponCode": "BUYGET1",
        "discountPercentage": "0.00",
        "couponType": "BxGy"
    },
    {
        "couponId": 4,
        "couponCode": "A430",
        "discountPercentage": "10.00",
        "couponType": "BxGy"
    },
    {
        "couponId": 7,
        "couponCode": "CB30",
        "discountPercentage": "30.00",
        "couponType": "Product_wise"
    }
]
```
---
- # **POST /apply-coupon/{id}: Apply a specific coupon to the cart and return the updated cart with discounted prices for each item**
  ### Yet to develop....




  
