<?php
    error_reporting(E_ALL); 
    ini_set('display_errors',1); 
	
	include('db.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {
     $st_num=$_POST['st_num'];
	 $cs_title=$_POST['cs_title'];
	 $cs_contents=$_POST['cs_contents'];

         if($st_num != NULL)
        {
            try{
			$stmt = $con->prepare("insert into community(cs_title, cs_contents, cs_date, st_num)
            values(:cs_title, :cs_contents, now(), :st_num)");
            
            $stmt->bindParam(':st_num', $st_num);
			$stmt->bindParam(':cs_title', $cs_title);
            $stmt->bindParam(':cs_contents', $cs_contents);
			
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
				cs_title: <input type = "text" st_num = "cs_title" />
				cs_contents: <input type = "text" st_num = "cs_contents" />
                <input type = "submit" st_num = "submit" />
           </form>
       
       </body>
    </html>

<?php 
    }
?>