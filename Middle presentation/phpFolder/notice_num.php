<?php
   include('db.php');
   
   $sql = "select * from notice";
   $re = $con->prepare($sql);
   $re->execute();
	  
   $num = $re->rowCount();
   echo $num;
?>