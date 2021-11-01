<?php 
    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('db.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {
		$st_Id=$_POST['st_Id'];
		$st_password=$_POST['st_password'];

        if($st_Id != NULL)
        {
            try{
            $sql ="select * from student where st_Id='$st_Id' and st_password='$st_password'"; 
            $stmt = $con->prepare($sql);
            $stmt->execute();
           
            if ($stmt->rowCount() == 0){
               echo "incorrect"; 
            }
            else{
               echo "correct";
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
                Name: <input type = "text" name = "st_Id" />
            password: <input type = "text" name = "st_password" />
                <input type = "submit" name = "submit" />
           </form>
       
       </body>
    </html>

<?php 
    }
?>