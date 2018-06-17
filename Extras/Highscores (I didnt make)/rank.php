<?php
$Location = '<a href="http://www.iHaxx.Net/highscores/index.php">Highscores</a> &gt; Personal Scores ';
include('includes/header.php');
?>
<?
showPlayerRank($_GET['rank']);
?>
<?php include('includes/footer.php'); ?>