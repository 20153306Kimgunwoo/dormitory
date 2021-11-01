<?php
   include('db.php');
   
   $sql = "select * from community";
   $re = $con->prepare($sql);
   $re->execute();
	  
   $num = $re->rowCount();
   echo $num;  
?>