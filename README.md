# CarPrice
This application calculates the mean price for new cars. Car price data is fetched from an API that Skatteverket has. 

<b>There is two ways to get the mean price information for a fueltype.</b>

1. If you just want JSON-data, you can go to:
```
"http://localhost:8080/car/get-mean/{fueltype}"
```

2. If you want to view the mean prices visually in a line chart, you can go to:
```
"http://localhost:8080/car/view/{fueltype}"
```

<b> Possible fueltypes: </b>
<ul>
<li>Bensin</li>
<li>Diesel</li>
<li>Gas</li>
<li>El</li>
<li>Elhybrid</li>
<li>Laddhybrid</li>
<li>Alkohol</li>
</ul>