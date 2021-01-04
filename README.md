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

## Request examples

Empty body

```json
{
  "number": "ABC",
  "status": "REGISTERED",
  "objects": []
}
```

Example 1:

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

Example 2:

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