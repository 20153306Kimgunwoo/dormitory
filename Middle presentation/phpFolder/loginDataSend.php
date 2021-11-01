<?php
    error_reporting(E_ALL); 
    ini_set('display_errors',1); 
	$servername = "localhost";
	$username = "root";
	$password = "";
	$database = "mokdorm";

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {
     $st_Id=$_POST['st_Id'];
	 
        if($st_Id != NULL)
        {
            try{
				$conn = new mysqli($servername, $username, $password, $database);
				if($conn->connect_error) {
					die("Connection failed: " . $conn->connect_error);
				}
				$heroes = array(); 
				$sql = "SELECT * FROM student WHERE st_Id LIKE '$st_Id'";
				$stmt = $conn->prepare($sql);
				$stmt->execute();
				$stmt->bind_result($st_num, $st_name, $st_idNum1, $st_idNum2, $st_Id, $st_password, $st_buildingNum, $st_roomNum, $st_phoneNum, $st_major);
 
				while($stmt->fetch()){	
					$temp = [
					'st_name'=>$st_name,
					'st_num'=>$st_num,
					'st_idNum1'=>$st_idNum1,
					'st_idNum2'=>$st_idNum2,
					'st_phoneNum'=>$st_phoneNum,
					'st_major'=>$st_major
					];
					array_push($heroes, $temp);
				}
				echo json_encode($heroes, JSON_UNESCAPED_UNICODE);
			} 
			catch(PDOException $e) {
				die("Database error: " . $e->getMessage()); 
			}
        }
   }
?>

<?php    
   $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");
   
    if( !$android )
    {
?>
    <html>
       <body>
           <form method="post" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>">
                st_Id: <input type = "text" name = "st_Id" />
                <input type = "submit" name = "submit" />
           </form>
       
       </body>
    </html>

<?php 
    }
?>