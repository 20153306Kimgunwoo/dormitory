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

        if($st_num != NULL)
        {
            try{
				$conn = new mysqli($servername, $username, $password, $database);
				if($conn->connect_error) {
					die("Connection failed: " . $conn->connect_error);
				}
				$heroes = array(); 
				$sql = "SELECT p_kind, p_point, p_reason, p_date FROM point WHERE st_num = '$st_num';";
				$stmt = $conn->prepare($sql);
				$stmt->execute();
				$stmt->bind_result($p_kind, $p_point, $p_reason, $p_date);
 
				while($stmt->fetch()){	
					$temp = [
					'p_kind'=>$p_kind,
					'p_point'=>$p_point,
					'p_reason'=>$p_reason,
					'p_date'=>$p_date
					];
					array_push($heroes, $temp);
				}
				echo json_encode($heroes, JSON_UNESCAPED_UNICODE);
            } catch(PDOException $e) {
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
                num: <input type = "text" name = "num" />
                <input type = "submit" name = "submit" />
           </form>
       
       </body>
    </html>

<?php 
    }
?>