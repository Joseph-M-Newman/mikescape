<?php
$Location = '<a href="'.$base_dir.'/index.php">Highscores</a> &gt; Compare Players';
$showLeft = 'false';
$showRight = 'false';
include('includes/header.php');

showComparePlayers($_GET['user1'],$_GET['user2']);
	
include('includes/footer.php');
?>