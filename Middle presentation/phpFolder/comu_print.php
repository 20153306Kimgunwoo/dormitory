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
     $num=$_POST['num'];

        if($num != NULL)
        {
            try{
				if($num == 0){
					$conn = new mysqli($servername, $username, $password, $database);
					if($conn->connect_error) {
						die("Connection failed: " . $conn->connect_error);
					}
					$heroes = array(); 
					$sql = "SELECT * FROM ( SELECT Row_Number() OVER (ORDER BY cs_date DESC) AS cs_num, st_num, cs_title, cs_date from community) community WHERE cs_num BETWEEN 1 AND 10;";
					$stmt = $conn->prepare($sql);
					$stmt->execute();
					$stmt->bind_result($cs_num, $st_num, $cs_title, $cs_date);
 
					while($stmt->fetch()){	
						$temp = [
						'st_num'=>$st_num,
						'cs_title'=>$cs_title,
						'cs_date'=>$cs_date
						];
						array_push($heroes, $temp);
					}
					echo json_encode($heroes, JSON_UNESCAPED_UNICODE);
				}
				else{
					$conn = new mysqli($servername, $username, $password, $database);
					if($conn->connect_error) {
						die("Connection failed: " . $conn->connect_error);
					}
					$num1 = $num*10+1;
					$num2 = $num1+9;
					$heroes = array(); 
					$sql = "SELECT * FROM ( SELECT Row_Number() OVER (ORDER BY cs_date DESC) AS cs_num, st_num, cs_title, cs_date from community) comu WHERE cs_num BETWEEN '$num1' AND '$num2'";
					$stmt = $conn->prepare($sql);
					$stmt->execute();
					$stmt->bind_result($cs_num, $st_num, $cs_title, $cs_date);
 
					while($stmt->fetch()){	
						$temp = [
						'st_num'=>$st_num,
						'cs_title'=>$cs_title,
						'cs_date'=>$cs_date
						];
						array_push($heroes, $temp);
					}
					echo json_encode($heroes, JSON_UNESCAPED_UNICODE);
				} 	 
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