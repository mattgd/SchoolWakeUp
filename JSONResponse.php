<?php
// If the school ID is found in the URL
if (!($_GET["id"] == "")) {
    
    // Consider getting this from an included file, also, the final file of this won't be on GitHub.
    $mysqli = new mysqli("domain", "user", "password", "swu"); 

    // MySQLi cannot connect
    if ($mysqli->connect_errno) {
        echo "Failed to connect to MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
    }
    
    // Prepare SQL statement
    if (!($stmt = $mysqli->prepare("SELECT * FROM school_data WHERE school_id = ?"))) {
        echo "Prepare failed: (" . $mysqli->errno . ") " . $mysqli->error;
    }
    
    // Bind SQL statement
    $school_id = $_GET["id"];
    if (!$stmt->bind_param("s", $school_id)) {
        echo "Binding parameters failed: (" . $stmt->errno . ") " . $stmt->error;
    }

    // Execute SQL statement
    if (!$stmt->execute()) {
        echo "Execute failed: (" . $stmt->errno . ") " . $stmt->error;
    } else {
        $school = array();
        
        while ($row = $stmt->fetch()) {
            $school["name"] = $row["city"];
            $school["address"] = $row["city"];
            $school["website"] = $row["city"];
            $school["start_time"] = $row["start"];
            $school["message"] = $row["message"];
        }
        
        // Check for empty array
        if (!(empty($school))) {
            echo json_encode($school);
        }
    }
    
}
?>
