Article with explanation in russian: https://habr.com/ru/articles/858040/

How to reproduce:

1. Create Order and add milestones to it:

```http request
POST localhost:8081/orders

{
"name": "Order"
}
```

```http request
POST localhost:8081/orders/1/milestones
[
    {
        "index": 1,
        "startDate": "2025-01-01",
        "endDate": "2025-01-02"
    },
    {
        "index": 2,
        "startDate": "2025-01-03",
        "endDate": "2025-01-04"
    },
    {
        "index": 3,
        "startDate": "2025-01-07",
        "endDate": "2025-01-08"
    },
    {
        "index": 4,
        "startDate": "2025-01-09",
        "endDate": "2025-01-10"
    }
]
```
2. Call update milestone endpoint to update 2nd and 3rd milestones in parallel:

```http request

PUT localhost:8081/orders/1/milestones/2
{
    "startDate": "2025-01-03",
    "endDate": "2025-01-06"
}
```
```http request

PUT localhost:8081/orders/1/milestones/3
{
    "startDate": "2025-01-05",
    "endDate": "2025-01-08"
}
```

You can achieve parallel execution by adding `Thread.sleep(5000)` at the end of the `MilestoneController.update()` method.
If you want to observe the original issue, just disable the `JpaConfig` class.