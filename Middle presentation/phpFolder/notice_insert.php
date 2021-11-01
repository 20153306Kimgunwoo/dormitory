<?php
    error_reporting(E_ALL); 
    ini_set('display_errors',1); 
	
	include('db.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {
	 $title=$_POST['title'];
	 $content=$_POST['content'];

         if($name != NULL)
        {
            try{
			$stmt = $con->prepare("insert into comu(title, content, hit)
            values(:title, :content, '2')");
            
			$stmt->bindParam(':title', $title);
            $stmt->bindParam(':content', $content);
			
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
				title: <input type = "text" name = "title" />
				content: <input type = "text" name = "content" />
                <input type = "submit" name = "submit" />
           </form>
       
       </body>
    </html>

<?php 
    }
?>