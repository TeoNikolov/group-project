<?php

$query = $_POST['query'];
$feedback = $_POST['feedback'];

if(feedback === "Yes") {
	$yes = "1";
	$no = "0";	
} else {
	$yes = "0";
	$no = "1";	
}

$servername = "csmysql.cs.cf.ac.uk";
$username = "group9.2016";
$password = "g4QFghm5Kxm";
$dbname = "group9_2016";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql = "SELECT id, name, Yes, No FROM Feedback WHERE name = '" . $query . "'";
$result = $conn->query($sql);

if (mysqli_num_rows($result) == 0) {
$qman = "INSERT INTO Feedback (name, yes, no)
VALUES ('$query', '$yes', '$no')";

if ($conn->query($qman) === TRUE) {
    echo "New record created successfully";
} else {
    echo "Error: " . $qman . "<br>" . $conn->error;
}
} else {
  echo "updating";
}
$conn->close();


// Create connection
$conn2 = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn2->connect_error) {
    die("Connection failed: " . $conn2->connect_error);
} 

$sql = "SELECT id, name, Yes, No FROM Feedback WHERE name = '" . $query . "'";
$result2 = $conn2->query($sql);

if (mysqli_num_rows($result2) > 0) {
while($row = mysqli_fetch_assoc($result2)) {
$name2 = $row["name"];
$yes2 = $row["Yes"];
$no2 = $row["No"];
$yes3 = $yes2 + $yes;
$no3 = $no2 + $no;
$qman2 = "UPDATE Feedback SET yes = '$yes3', no = '$no3' WHERE name = '$name2'";
if ($conn2->query($qman2) === TRUE) {
    echo "New record created successfully";
} else {
    echo "Error: " . $qman . "<br>" . $conn->error;
}

}
} else { echo "No results"; }


$conn->close();
?>