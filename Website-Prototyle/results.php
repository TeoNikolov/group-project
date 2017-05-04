<!DOCTYPE HTML>
<html>
<head>
<title>Compass</title>
<meta name="viewport" content="width=device-width,initial-scale=1.0">

<!-- Custom Theme files -->
<link href="css/styleResults.css" rel="stylesheet" type="text/css" media="all"/>
<link href="https://fonts.googleapis.com/css?family=Abel" rel="stylesheet">
<!--     <link href="css/bootstrap.min.css" rel="stylesheet">
 -->


</head>
<body>

<div class="container">
  <div class="topbar"></div>
  <div class="bottombar"></div>
  <div class="item-1">
  <a href="index.html">
    <img src="images/compass.png" alt="compass" >
  </a>
  </div>
  <div class="item-2">
        <form action="results.html">
      <input type="search" placeholder="Search Cardiff...">
    </form>
  </div>
</div>

 <?php
$servername = "csmysql.cs.cf.ac.uk";
$username = "group9.2016";
$password = "g4QFghm5Kxm";
$dbname = "group9_2016";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
echo "Connected successfully";
?>

</body>
</html>