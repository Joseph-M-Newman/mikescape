<?php
include('mysql.php');
include('functions.php');
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en">
<head>
<title>High Scores</title>
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="-1" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="generator" content="vBulletin 3.8.1" />
<style type="text/css">
@import "css/vb.css";
@import "css/main.css";
@import "css/highscores.css";
</style>
</head>
<body>
<script type="text/javascript" src="<?php echo $base_dir?>jquery.js"></script>
<script type="text/javascript">
function update()
{	
    $.post("/online.php", {}, function(data){ $("#online_players").html(data);});
	setTimeout('update()', 3000);
}

$(document).ready(
 
function() 
    {
	   update();
	   layershowhide('Enlarged_Image_Box', 'show');
    });
</script>
<a name="top"></a>
    <div align="center">
        <div class="page" style="width:830px; text-align:left">
            <div style="padding: 10px 10px 0px 10px;" align="left">

            <table class="tborder" cellpadding="6" cellspacing="1" border="0" width="100%" align="center">
            <tr>
            <td class="tcat" align="center"><a href="#top" onClick="return toggle_collapse('forumhome_infernoshout')">Project Poanizer Highscores</td>
            </tr>
            <tbody id="collapseobj_forumhome_infernoshout">
            <tr>
            <td class="alt1" width="100%">
                <table align="center" width="100%" cellpadding="0" cellspacing="0" style="padding-bottom: 5px;">
                <tr>
                <td>
                    <table width="100%" cellpadding="2" cellspacing="0">
                    <tr>
                    <?php if ($showLeft == "false") { } else { ?>
                    <td width="163" valign="top">
                        <div class="subsectionHeader">Skills</div>
                        <div id="skillsList" style="width: 163px;">
                        <ul>
                        <li style="background-color:#4c350a;"><a href="index.php" target="_self" class="Overall ico">Overall</a></li>
                        <li><a href="skills.php?type=1" target="_self" class="   Attack    ico">Attack</a></li>
                        <li><a href="skills.php?type=2" target="_self" class="   Defence    ico">Defence</a></li>
                        <li><a href="skills.php?type=3" target="_self" class="   Strength    ico">Strength</a></li>
                        <li><a href="skills.php?type=4" target="_self" class="   Hitpoints    ico">Hitpoints</a></li>
                        <li><a href="skills.php?type=5" target="_self" class="   Ranged    ico">Ranged</a></li>
                        <li><a href="skills.php?type=6" target="_self" class="   Prayer    ico">Prayer</a></li>
                        <li><a href="skills.php?type=7" target="_self" class="   Magic    ico">Magic</a></li>
                        <li><a href="skills.php?type=8" target="_self" class="   Cooking    ico">Cooking</a></li>
                        <li><a href="skills.php?type=9" target="_self" class="   Woodcutting    ico">Woodcutting</a></li>
                        <li><a href="skills.php?type=10" target="_self" class="   Fletching    ico">Fletching</a></li>
                        <li><a href="skills.php?type=11" target="_self" class="   Fishing    ico">Fishing</a></li>
                        <li><a href="skills.php?type=12" target="_self" class="   Firemaking    ico">Firemaking</a></li>
                        <li><a href="skills.php?type=13" target="_self" class="   Crafting    ico">Crafting</a></li>
                        <li><a href="skills.php?type=14" target="_self" class="   Smithing    ico">Smithing</a></li>
                        <li><a href="skills.php?type=15" target="_self" class="   Mining    ico">Mining</a></li>
                        <li><a href="skills.php?type=16" target="_self" class="   Herblore    ico">Herblore</a></li>
                        <li><a href="skills.php?type=17" target="_self" class="   Agility    ico">Agility</a></li>
                        <li><a href="skills.php?type=18" target="_self" class="   Thieving    ico">Thieving</a></li>
                        <li><a href="skills.php?type=19" target="_self" class="   Slayer    ico">Slayer</a></li>
                        <li><a href="skills.php?type=20" target="_self" class="   Farming    ico">Farming</a></li>
                        <li><a href="skills.php?type=21" target="_self" class="Runecraft">Runecrafting</a></li>
                        </ul>
                        </div>
                    </td>
                    <?php } ?>
                    <td width="100%" valign="top">