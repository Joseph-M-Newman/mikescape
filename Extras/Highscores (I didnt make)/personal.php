<?php
$Location = '<a href="'.$base_dir.'/index.php">Highscores</a> &gt; Personal Scores';
include('includes/header.php');
?>
<?
showPlayer($_GET['character']);
?>
<?php include('includes/footer.php'); ?>