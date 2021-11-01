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
     $st_num=$_POST['st_num'];
	 $cs_date=$_POST['cs_date'];

        if($cs_date != NULL)
        {
            try{
				$conn = new mysqli($servername, $username, $password, $database);
				if($conn->connect_error) {
					die("Connection failed: " . $conn->connect_error);
				}
				$heroes = array(); 
				$sql = "SELECT cs_contents FROM community WHERE st_num LIKE '$st_num' AND cs_date = '$cs_date'";
				$stmt = $conn->prepare($sql);
				$stmt->execute();
				$stmt->bind_result($cs_contents);
 
				while($stmt->fetch()){	
					$temp = [
					'cs_contents'=>$cs_contents
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
                st_num: <input type = "text" st_num = "st_num" />
				cs_date: <input type = "text" st_num = "cs_date" />
                <input type = "submit" st_num = "submit" />
           </form>
       
       </body>
    </html>

<?php 
    }
?>