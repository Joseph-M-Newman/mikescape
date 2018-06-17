<?php
// MYSQL
mysql_connect('localhost', 'poanizer_hs', 'demscoresgx');
mysql_select_db('poanizer_highscores');

// CONFIG
$ppls_page = "20";
$sig_support = false;
$maxhiscorelevel = 99;
$sig_image = "";
$top_hiscore = 1000001;
$minlvltohiscore = 10;
$website = "http://www.poanizer.com/highscores";
$webtwo = "http://www.poanizer.com/";
$images = "http://www.poanizer.com/images/";
$file = "index.php";
$file2 = "skills.php";
$pers = "personal.php";

$base_dir = "/"; // where highscores is installed
$img_dir = "/images/"; // highscore image directory

// ARRAYS
$webbers	= array("", "", "");
$admins		= array("");
$mods		= array("");
$high_mods	= array("");
$donators	= array("");
$banned		= array("");

// ICONS
$webbers_icon	= $img_dir."moderator.gif";
$admins_icon	= $img_dir."admin.gif";
$mods_icon	= $img_dir."moderator.gif";
$high_mods_icon	= $img_dir."moddws8.gif"; //http://img264.imageshack.us/img264/3287/moddws8.gif
$donators_icon	= $img_dir."donator.gif";

// BBCODE
$webbers_code	= '<img src="http://www.poanizer.com/highscores/images/moderator.gif" border="0" />';
$admins_code	= '<img src="http://www.poanizer.com/highscores/images/admin.gif" border="0" />';
$mods_code	= '<img src="http://www.poanizer.com/highscores/images/moderator.gif" border="0" />';
$high_mods_code	= '<img src="http://i34.tinypic.com/1zeccg9.jpg" border="0" />';
$donators_code	= '<img src="http://www.poanizer.com/highscores/images/donator.gif" border="0" />';

global $img_dir, $website;
// Forum login check
//$Forum_Main_LoggedIn_Check = mysql_query("SELECT * FROM session WHERE sessionhash='".$_COOKIE['bb_sessionhash']."' AND loggedin='2'");
//if (mysql_num_rows($Forum_Main_LoggedIn_Check) > 0) {
//	$Forum_Main_LoggedIn_Check = "TRUE88y2r2uhwfheufhwkjfh".$_COOKIE['bb_sessionhash'];
//} else {
//	$Forum_Main_LoggedIn_Check = "FALSE";
//}

//print 'cookies: '.$_SESSION;
?>