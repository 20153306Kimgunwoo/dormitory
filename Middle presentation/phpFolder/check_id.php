<?php 
    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('db.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {
      $st_Id=$_POST['st_Id'];
    
        if($st_Id != NULL)
        {
            try{
            $sql ="select * from student where st_Id='$st_Id'"; 
            $stmt = $con->prepare($sql);
            $stmt->execute();
         
            if ($stmt->rowCount() == 0){
               echo "successed";
            }
            else{
               echo "falied";
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
                Name: <input type = "text" name = "id" />
            password: <input type = "text" name = "password" />
                <input type = "submit" name = "submit" />
           </form>
       
       </body>
    </html>

<?php 
    }
?>