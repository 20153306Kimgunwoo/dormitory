<?php
    error_reporting(E_ALL); 
    ini_set('display_errors',1); 
	
	include('db.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {
	 $st_num=$_POST['st_num'];
     $o_reason=$_POST['o_reason'];
	 $o_date=$_POST['o_date'];
	 $o_kind=$_POST['o_kind'];

         if($st_num != NULL)
        {
            try{
			$stmt = $con->prepare("insert into goOut(o_reason, o_date, o_kind, st_num)
            values(:o_reason, :o_date, :o_kind, :st_num)");
            
			$stmt->bindParam(':o_reason', $o_reason);
            $stmt->bindParam(':o_date', $o_date);
			$stmt->bindParam(':o_kind', $o_kind);
			$stmt->bindParam(':st_num', $st_num);
			
			
            if($stmt->execute()) {
               echo "successed";
            }
            else {
               echo "failed";
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
                st_num: <input type = "text" st_num = "st_num" />
				o_date: <input type = "text" st_num = "o_date" />
				o_reason: <input type = "text" st_num = "o_reason" />
                <input type = "submit" st_num = "submit" />
           </form>
       
       </body>
    </html>

<?php 
    }
?>