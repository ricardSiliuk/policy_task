# Policy premium calculator

---

This is a spring boot application running on port 8080 that calculates premium on a given policy.

Policy should be submitted via `POST` to <http://localhost:8080/calculatePremium>

## Comments on the implementation

No special error handling apart from what Spring provides out of the box was added, so providing null objects will fail.
Depending on the usecase this is either a non-issue or should be handled with extra checks, and if it's a bigger project
with a global exception handler.

`PremiumCalculatorService` is a `@Component` but might as well be a `static` class. `@Component` would make more sense
if specific calculators for THEFT/FIRE/etc. were separate components but only to an extent as soon it would have too
many subcomponents. Since current risk-type calculators are quite small there is no need to externalize them.

`PremiumCalculatorService.calculate()` rounds up to 2 digits after a decimal separator. Ideally it should return
raw `BigDecimal` and let the end user decide on required precision.

Since it was not explicitly stated I defaulted empty policies to have premium of 0. It could also be defaulted to null.

## Request examples

### Empty body

Request body:

```json
{
  "number": "ABC",
  "status": "REGISTERED",
  "objects": []
}
```

Response:

```json
{
  "premium": 0.00
}
```

### Example 1

Request body:

```json
{
  "number": "DEF",
  "status": "REGISTERED",
  "objects": [
    {
      "name": "House",
      "subObjects": [
        {
          "name": "TV",
          "sumInsured": 100,
          "riskType": "FIRE"
        },
        {
          "name": "DVD",
          "sumInsured": 8,
          "riskType": "THEFT"
        }
      ]
    }
  ]
}
```

Response:

```json
{
  "premium": 2.28
}
```

### Example 2

Request body:

```json
{
  "number": "GHI",
  "status": "APPROVED",
  "objects": [
    {
      "name": "House",
      "subObjects": [
        {
          "name": "TV",
          "sumInsured": 500,
          "riskType": "FIRE"
        },
        {
          "name": "DVD",
          "sumInsured": 102.51,
          "riskType": "THEFT"
        }
      ]
    }
  ]
}
```

Response:

```json
{
  "premium": 17.13
}
```