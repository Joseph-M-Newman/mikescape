<?php include('includes/header.php'); ?>
<div id="playerList_back">
<div id="scores_head" class="subsectionHeader" style="padding: 0px;"><img style="margin-top: 3px;" class="miniimg_list" id="image_caption" src="<?php echo $img_dir?>/skill_icon_overall1.gif">Overall Highscores</div>
<?
if(!$_GET['playerName']) {
    if(!$_GET['name']) {
        if($_GET['type'] != "0" && $_GET['type'] != "" && $_GET['type'] != "PK") {
        
            showPlayers($_GET['type']);
        } else if($_GET['type'] == "PK") {
            PkRating();
        } else {
            OverallHiscore();
        }
    } else {
        showPlayer($_GET['name']);
    }
} else {
	showSig($_GET['playerName']);
}
?>
</div>
<?php include('includes/footer.php'); ?>