<?php 
    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('db.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {    
     $st_num=(int)$_POST['st_num'];
	 $st_name=$_POST['st_name'];
     $st_Id=$_POST['st_Id'];
     $st_password=$_POST['st_password'];
	 $st_idNum1=(int)$_POST['st_idNum1'];
	 $st_idNum2=(int)$_POST['st_idNum2'];
     $st_phoneNum=$_POST['st_phoneNum'];
	 $st_major=$_POST['st_major'];
	 
        if($st_num != NULL)
        {
            try{
            $stmt = $con->prepare('insert into student(st_num, st_name, st_Id, st_password, st_idNum1, st_idNum2, 
				st_phoneNum, st_major)
            values(:st_num, :st_name, :st_Id, :st_password, :st_idNum1, :st_idNum2, :st_phoneNum, :st_major)');
            
            $stmt->bindParam(':st_num', $st_num);
            $stmt->bindParam(':st_name', $st_name);
            $stmt->bindParam(':st_Id', $st_Id);
            $stmt->bindParam(':st_password', $st_password);
            $stmt->bindParam(':st_idNum1', $st_idNum1);
            $stmt->bindParam(':st_idNum2', $st_idNum2);
			$stmt->bindParam(':st_phoneNum', $st_phoneNum);
			$stmt->bindParam(':st_major', $st_major);
            
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
                Name: <input type = "text" name = "id" />
            password: <input type = "text" name = "password" />
                <input type = "submit" name = "submit" />
           </form>
       
       </body>
    </html>

<?php 
    }
?>