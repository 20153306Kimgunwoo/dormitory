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
	 $n_date=$_POST['n_date'];

        if($n_date != NULL)
        {
            try{
				$conn = new mysqli($servername, $username, $password, $database);
				if($conn->connect_error) {
					die("Connection failed: " . $conn->connect_error);
				}
				$heroes = array(); 
				$sql = "SELECT n_contents FROM notice WHERE n_date = '$n_date'";
				$stmt = $conn->prepare($sql);
				$stmt->execute();
				$stmt->bind_result($n_contents);
 
				while($stmt->fetch()){	
					$temp = [
					'n_contents'=>$n_contents
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
				n_date: <input type = "text" name = "n_date" />
                <input type = "submit" name = "submit" />
           </form>
       
       </body>
    </html>

<?php 
    }
?>