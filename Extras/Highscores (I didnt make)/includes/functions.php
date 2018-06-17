<?php

include('mysql.php');

function getLevelForXP($xp) {
	$lvl = 1;
	if(!$xp || $xp==0) $xp = 1;

	while($xp > $output){
		$points = $points + floor($lvl + 300 * pow(2, ($lvl / 7)));
		$output = floor($points / 4);
		if($lvl > 99){
			return 99;
			break;
		} else if($xp > 13034430){
			return 99;
			break;
		} else if($xp < 83){
			return 1;
			break;
		} else if($output >= $xp){
			return $lvl;
			break;
		}
		$lvl++;
	}
}
function findType ($type) {
	if($type == "" && $type != "PK") {
		return "Hitpoints";
	} else if($type == "0") {
		return "Hitpoints";
	} else if($type == "1") {
		return "Attack";
	} else if($type == "2") {
		return "Defence";
	} else if($type == "3") {
		return "Strength";
	} else if($type == "4") {
		return "Hitpoints";
	} else if($type == "5") {
		return "Ranged";
	} else if($type == "6") {
		return "Prayer";
	} else if($type == "7") {
		return "Magic";
	} else if($type == "8") {
		return "Cooking";
	} else if($type == "9") {
		return "Woodcutting";
	} else if($type == "10") {
		return "Fletching";
	} else if($type == "11") {
		return "Fishing";
	} else if($type == "12") {
		return "Firemaking";
	} else if($type == "13") {
		return "Crafting";
	} else if($type == "14") {
		return "Smithing";
	} else if($type == "15") {
		return "Mining";
	} else if($type == "16") {
		return "Herblore";
	} else if($type == "17") {
		return "Agility";
	} else if($type == "18") {
		return "Thieving";
	} else if($type == "19") {
		return "Slayer";
	} else if($type == "20") {
		return "Farming";
	} else if($type == "21") {
		return "Runecraft";
	} else if($type == "PK") {
		return "PK Rating";
	}
		
}

function fixName($name) {
	return strtolower(mysql_real_escape_string($name));
}

function dots($num) {
	if($num) {
		$num = number_format($num);
	} else {
		$num = "0";
	}
	return $num;
}
function findRank($playerName,$skill) {
	$playerName = fixName($playerName);
	if(!$top_hiscore) { $top_hiscore = "100"; }
	
	if($skill != "0" && $skill != "22") {
		$i=1;
		$xptype = findType($skill)."xp";
		$query = mysql_query("SELECT * FROM skills ORDER BY $xptype DESC") or die(mysql_error());

		while($row = mysql_fetch_array($query)){
			if(fixName($row["playerName"]) == $playerName) {
				if($i > $top_hiscore || $row[''.$xptype.''] == "0") {
					return '<i>Not&nbsp;Ranked</i>';
				} else {
					return '<b>'.$i.'</b>';
				}
			}
			$i++;
		}
	} else if ($skill == "22") {
		$i=1;
		$query = mysql_query("SELECT * FROM points ORDER BY pkPoints DESC") or die(mysql_error());

		while($row = mysql_fetch_array($query)){
			if(fixName($row["playerName"]) == $playerName) {
				if($i > $top_hiscore || $row[''.$xptype.''] == "0") {
					return '<i>Not&nbsp;Ranked</i>';
				} else {
					return '<b>'.$i.'</b>';
				}
			}
			$i++;
		}
	} else {
		$i=1;
		$query = mysql_query ("SELECT * FROM skillsoverall ORDER BY lvl DESC") or die(mysql_error());
		while($row = mysql_fetch_array($query)){
			if(fixName($row["playerName"]) == $playerName) {
				if($i > $top_hiscore) {
					return '<i>Not&nbsp;Ranked</i>';
				} else {
					return '<b>'.$i.'</b>';
				}
			}
			$i++;
		}
	}
}

function getCompare($ranktype,$user1,$user2) {
	if ($ranktype == "0") {
		$user1_query = mysql_query("SELECT * FROM skillsoverall WHERE playerName='".$user1."' LIMIT 1") or die(mysql_error());
		while($user1 = mysql_fetch_array($user1_query)){
			$user1_rank = $user1['lvl'];
			$user1_name = $user1['playerName'];
		}
		$user2_query = mysql_query("SELECT * FROM skillsoverall WHERE playerName='".$user2."' LIMIT 1") or die(mysql_error());
		while($user2 = mysql_fetch_array($user2_query)){
			$user2_rank = $user2['lvl'];
			$user2_name = $user2['playerName'];
		}
	} else {
		$ranktype = findType($ranktype)."lvl";
		$user1_query = mysql_query("SELECT * FROM skills WHERE playerName='".$user1."' LIMIT 1") or die(mysql_error());
		while($user1 = mysql_fetch_array($user1_query)){
			$user1_rank = $user1[''.$ranktype.''];
			$user1_name = $user1['playerName'];
		}
		$user2_query = mysql_query("SELECT * FROM skills WHERE playerName='".$user2."' LIMIT 1") or die(mysql_error());
		while($user2 = mysql_fetch_array($user2_query)){
			$user2_rank = $user2[''.$ranktype.''];
			$user2_name = $user2['playerName'];
		}
	}
	if ($user1_rank > $user2_rank) {
		return '<img src="images/arrowup.gif" title="'.$user1_name.' has a higher level in this category than '.$user2_name.'" />';
	} else if ($user2_rank > $user1_rank) {
		return '<img src="images/arrowdown.gif" title="'.$user2_name.' has a higher level in this category than '.$user1_name.'" />';
	} else if ($user2_rank == $user1_rank) {
		return '<img src="images/arrowequal.gif" title="'.$user1_name.' has the same level in this category as '.$user2_name.'" />';
	}
}

function BBCode($playerName) {
	include "mysql.php";
	if (in_array(fixName($playerName), $webbers)) {
		return $webbers_code.'<font color="#3BB9FF"><b>'.ucwords($playerName).'</b></font>';
	} else if (in_array(fixName($playerName), $admins)) {
		return $admins_code.'<font color="#f7fe11"><b>'.ucwords($playerName).'</b></font>';
	} else if (in_array(fixName($playerName), $high_mods)) {
		return $high_mods_code.ucwords($playerName);
	} else if (in_array(fixName($playerName), $mods)) {
		return $mods_code.ucwords($playerName);
	} else if (in_array(fixName($playerName), $donators)) {
		return $donators_code.'<font color="red"><b>'.ucwords($playerName).'</b></font>';
	} else {
		return ucwords($playerName);
	}
}

function showSig($playerName) {
	include "mysql.php";
	if($sig_support == "true") {
		$query = mysql_query("SELECT * FROM skills WHERE playerName = '".$playerName."' LIMIT 1") or die(mysql_error());
		$row = mysql_fetch_array($query);
		if(isset($playerName) && isset($row["playerName"]) && !in_array(fixName($row["playerName"]), $banned)) {
			echo "Use this image link to show your signature<br />";
			echo "<input type='text' size='100' value='".$website."/image.php?playerName=".$playerName."' class='button'><br /><br />";
			echo "<b>Demo:</b><br />";
			echo "<img src='".$website."/image.php?playerName=".$playerName."'>";
		} else {
			echo "The user dosent exist";
		}
	} else {
		echo "Signatures isent supported.";
	}
}
function Pkrating() {
	include "mysql.php";
	if(!$top_hiscore) { $top_hiscore = "101"; }

		echo '<table class="table_back" width="100%">';
		echo '<tr class="table_header">';
		echo '<th class="rankHead">Rank</th>';
		echo '<th class="nameHead">Name</th>';
		echo '<th class="scoreHead">Score</th>';
		echo '</tr>';
		
		$query = mysql_query("SELECT COUNT(*) FROM points");
		if ($query) $count = mysql_result($query,0);
		$from = (isset($_GET["from"]) && is_numeric($_GET["from"]) && $_GET["from"] < $count) ? $_GET["from"] : 0;
		$query = mysql_query ("SELECT * FROM points ORDER BY pkPoints DESC LIMIT $from, $ppls_page") or die(mysql_error());
	
		$i = $from;
		while($row = mysql_fetch_array($query)){
			$i++;
			if($i < $top_hiscore) {
				if($i & 1) {
					echo '<tr class="row row1">';
					echo '<td class="rankCol">'.$i.'</td>';
					echo '<td class="alL"><a href="'.$website.'/'.$pers.'?name='.$row["playerName"].'" target="_self">'.BBCode($row["playerName"]).'</a></td>';
					echo '<td class="alL">'.dots($row["pkPoints"]).'</td>';
					echo '</tr>';
				} else {
					echo '<tr class="row row2">';
					echo '<td class="rankCol">'.$i.'</td>';
					echo '<td class="alL"><a href="'.$website.'/'.$pers.'?name='.$row["playerName"].'" target="_self">'.BBCode($row["playerName"]).'</a></td>';
					echo '<td class="alL">'.dots($row["pkPoints"]).'</td>';
					echo '</tr>';
				}
			}
		}
		echo '</table>';
	if($count >= $ppls_page) {
		echo '<table border="0" width="100%">';
		echo '<tr>';
		echo '<td align="center">';
		if ($from > 0) {
			$back= $from - $ppls_page;
			echo '<a href="'.$website.'/'.$file.'?type=0&from='.$back.'"><b>Back</b></a>';
		}
		$page = 1;

		for ($start = 0; $count > $start; $start = $start + $ppls_page) {
			/*if($from != $page * $ppls_page - $ppls_page) {
				if($start < $top_hiscore) {
					echo '<a href="'.$website.'/'.$file.'?type=0&from='.$start.'"><b>'.$page.'</b></a> ';
				}
			} else {
				echo $page.' ';
			}*/
			$page++;
		}

		if ($from < $count - $ppls_page || $from < $top_hiscore) {
			$next = $from + $ppls_page;
			if($next < $top_hiscore) {
				echo '<a href="'.$website.'/'.$file.'?type=0&from='.$next.'"><b>Next</b></a>';
			}
		}
		echo '</td>';
		echo '</tr>';
		echo '</table>';
	}
}

function Roguerating() {
	include 'mysql.php';
	if (!$top_hiscore) { $top_hiscore = "101"; }
	
	echo '<table class="table_back">';
	echo '<tr class="table_header">';
	echo '<th class="rankHead">Rank</th>';
	echo '<th class="nameHead">Name</th>';
	echo '<th class="scoreHead">Score</th>';
	echo '</tr>';
	
	$query = mysql_query("SELECT COUNT(*) FROM skills");
	if ($query) $count = mysql_result($query,0);
	$from = (isset($_GET["from"]) && is_numeric($_GET["from"]) && $_GET["from"] < $count) ? $_GET["from"] : 0;
	$query = mysql_query ("SELECT * FROM skills ORDER BY Score DESC LIMIT $from, $ppls_page") or die(mysql_error());

	$i = $from;
	while($row = mysql_fetch_array($query)){
		$i++;
		if($i < $top_hiscore) {
			if($i & 1) {
				echo '<tr class="row row1">';
				echo '<td class="rankCol">'.$i.'</td>';
				echo '<td class="alL"><a href="'.$website.'/'.$pers.'?name='.$row["playerName"].'" target="_self">'.BBCode($row["playerName"]).'</a></td>';
				echo '<td class="alL">'.dots($row["Score"]).'</td>';
				echo '</tr>';
			} else {
				echo '<tr class="row row2">';
				echo '<td class="rankCol">'.$i.'</td>';
				echo '<td class="alL"><a href="'.$website.'/'.$pers.'?name='.$row["playerName"].'" target="_self">'.BBCode($row["playerName"]).'</a></td>';
				echo '<td class="alL">'.dots($row["Score"]).'</td>';
				echo '</tr>';
			}
		}
	}
	echo '</table>';
	
	if($count >= $ppls_page) {
		echo '<table border="0" width="100%">';
		echo '<tr>';
		echo '<td align="center">';
		if ($from > 0) {
			$back= $from - $ppls_page;
			echo '<a href="'.$website.'/'.$file.'?type=0&from='.$back.'"><b>Back</b></a> ';
		}
		$page = 1;

		for ($start = 0; $count > $start; $start = $start + $ppls_page) {
			/*if($from != $page * $ppls_page - $ppls_page) {
				if($start < $top_hiscore) {
					echo '<a href="'.$website.'/'.$file.'?type=0&from='.$start.'"><b>'.$page.'</b></a> ';
				}
			} else {
				echo $page.' ';
			}*/
			$page++;
		}

		if ($from < $count - $ppls_page || $from < $top_hiscore) {
			$next = $from + $ppls_page;
			if($next < $top_hiscore) {
				echo ' <a href="'.$website.'/'.$file.'?type=0&from='.$next.'"><b>Next</b></a>';
			}
		}
		echo '</td>';
		echo '</tr>';
		echo '</table>';
	}
}

function OverallHiscore() {
	include 'mysql.php';
	if(!$top_hiscore) { $top_hiscore = "101"; }
	
	echo '<table width="100%" height="410" cellpadding="0" cellspacing="0">';
	echo '<tr>';
	echo '<td width="100%" valign="top" style="padding: 3px; padding-left: 1px">';
	echo '	<table width="100%" class="table_back" cellpadding="0" cellspacing="0">';
	echo '	<tr class="table_header">';
	echo '	<th class="rankHead">Rank</th>';
	echo '	<th class="nameHead">Name</th>';
	echo '	<th class="levelHead">Level</th>';
	echo '	<th class="xpHead">XP</th>';
	echo '	</tr>';
	
	$query = mysql_query("SELECT COUNT(*) FROM skillsoverall");
	if ($query) $count = mysql_result($query,0);

	$from = (isset($_GET["from"]) && is_numeric($_GET["from"]) && $_GET["from"] < $count) ? $_GET["from"] : 0;

	$query = mysql_query("SELECT * FROM skillsoverall ORDER BY lvl DESC LIMIT $from, $ppls_page") or die(mysql_error());

	$i = $from;
	if (mysql_num_rows($query)) {
		while($row = mysql_fetch_array($query)) {
			$i++;
			if($i < $top_hiscore) {
				if($i & 1) {
					echo '	<tr class="row row1">';
					echo '	<td class="rankCol">'.$i.'</td>';
					echo '	<td class="alL"><a href="'.$website.'/'.$pers.'?character='.$row["playerName"].'" target="_self">'.BBCode($row["playerName"]).'</a></td>';
					echo '	<td class="alL">'.dots($row["lvl"]).'</td>';
					echo '	<td class="alL">'.dots($row["xp"]).'</td>';
					echo '	</tr>';
				} else {
					echo '	<tr class="row row2">';
					echo '	<td class="rankCol">'.$i.'</td>';
					echo '	<td class="alL"><a href="'.$website.'/'.$pers.'?character='.$row["playerName"].'" target="_self">'.BBCode($row["playerName"]).'</a></td>';
					echo '	<td class="alL">'.dots($row["lvl"]).'</td>';
					echo '	<td class="alL">'.dots($row["xp"]).'</td>';
					echo '	</tr>';
				}
			}
		}
	}
	echo '	</table>';
	echo '	</td>';
	echo '	<td valign="middle">';
	
	if($count >= $ppls_page) {
		echo '<table cellpadding="0" cellspacing="0">';
		
		if ($from > 0) {
			$back= $from - $ppls_page;
			echo '	<tr>';
			echo '	<td align="center"><a href="'.$website.'/index.php?type=0&from='.$back.'"><img src="'.$img_dir.'arrow_up.gif" border="0" /></a></td>';
			echo '	</tr>';
		} else if ($from == 0 || $from == "") {
			echo '	<tr>';
			echo '	<td align="center"><img src="'.$img_dir.'arrow_up1.gif" border="0" /></td>';
			echo '	</tr>';
		}
		
		
		$page = 1;

		for ($start = 0; $count > $start; $start = $start + $ppls_page) {
			/*if($from != $page * $ppls_page - $ppls_page) {
				if($start < $top_hiscore) {
					echo '<a href="'.$website.'/'.$file.'?type=0&from='.$start.'"><b>'.$page.'</b></a> ';
				}
			} else {
				echo $page.' ';
			}*/
			$page++;
		}
		
		if ($from+$page == $count) {
			echo '	<tr>';
			echo '	<td align="center"><img src="'.$img_dir.'arrow_down1.gif" border="0" /></td>';
			echo '	</tr>';
		} else if ($from < $count - $ppls_page || $from < $top_hiscore) {
			$next = $from + $ppls_page;
			if($next < $top_hiscore) {
				echo '	<tr>';
				echo '	<td align="center"><a href="'.$website.'/index.php?type=0&from='.$next.'"><img src="'.$img_dir.'arrow_down.gif" border="0" /></a></td>';
				echo '	</tr>';
			}
		}
		
		echo '	</table>';
	}
	echo '</td>';
	echo '</tr>';
	echo '</table>';
}

function showPlayers($type) {
	include 'mysql.php';
	if(!$top_hiscore) { $top_hiscore = "101"; }
	echo '<div id="playerList_back">';
	echo '<div id="scores_head" class="subsectionHeader" style="padding: 0px;"><img src="'.$img_dir.'skill_icon_'.strtolower(findType($type)).'1.gif"> '.ucwords(findType($type)).' HighScores</div>';
	echo '<table width="100%" class="table_back">';
	echo '<tr class="table_header">';
	echo '<td class="rankHead">Rank</td>';
	echo '<td class="nameHead">Name</td>';
	echo '<td class="levelHead">Level</td>';
	echo '<td class="xpHead">XP</td>';
	echo '</tr>';

	$xptype = findType($type)."xp";
	$query = mysql_query("SELECT COUNT(*) FROM skills");
	if ($query) $count = mysql_result($query,0);
	$from = (isset($_GET["from"]) && is_numeric($_GET["from"]) && $_GET["from"] < $count) ? $_GET["from"] : 0;
	$query = mysql_query ("SELECT * FROM skills ORDER BY $xptype DESC limit $from, $ppls_page") or die(mysql_error());
	$i = $from;
	while($row = mysql_fetch_array($query)){
		$i++;
		if($i < $top_hiscore && !in_array($row["playerName"], $banned) && !in_array(ucwords($row["playerName"]), $banned)) {
			if($i & 1) {
				echo '<tr class="row row1">';
				echo '<td class="rankCol">'.$i.'</td>';
				echo '<td class="alL"><a href="'.$website.'/'.$pers.'?character='.$row["playerName"].'" target="_self">'.BBCode($row["playerName"]).'</a></td>';
				echo '<td class="alL">'.getLevelForXP($row[$xptype]).'</td>';
				echo '<td class="alL">'.dots($row[$xptype]).'</td>';
				echo '</tr>';
			} else {
				echo '<tr class="row row2">';
				echo '<td class="rankCol">'.$i.'</td>';
				echo '<td class="alL"><a href="'.$website.'/'.$pers.'?character='.$row["playerName"].'" target="_self">'.BBCode($row["playerName"]).'</a></td>';
				echo '<td class="alL">'.getLevelForXP($row[$xptype]).'</td>';
				echo '<td class="alL">'.dots($row[$xptype]).'</td>';
				echo '</tr>';
			}
		}
		
	}
	echo '</table>';
	
	if ($count >= $ppls_page) {
		echo '<table width="100%">';
		echo '<tr>';
		echo '<td align="center">';
		if ($from > 0) {
			$back= $from - $ppls_page;
			echo '<a href="'.$website.'/'.$file.'?type='.$_GET["type"].'&from='.$back.'"><b>Back</b></a>';
		}
		$page = 1;
		for ($start = 0; $count > $start; $start = $start + $ppls_page) {
			/*if($from != $page * $ppls_page - $ppls_page) {
				if($start < $top_hiscore) {
					echo '<a href="'.$website.'/'.$file.'?type='.$_GET["type"].'&from='.$start.'"><b>'.$page.'</b></a> ';
				}
			} else {
				echo $page." ";
			}*/
			$page++;
		}
	
		if ($from < $count - $ppls_page || $from < $top_hiscore) {
			$next = $from + $ppls_page;
			if($next < $top_hiscore) {
				echo '<a href="'.$website.'/'.$file.'?type='.$_GET["type"].'&from='.$next.'"><b>Next</b></a>';
			}
		}
		
		echo '</td>';
		echo '</tr>';
		echo '</table>';
	}
	echo '</div>';
}

function showPlayer($name) {
	include "mysql.php";
	global $website, $img_dir;
	$query = mysql_query("SELECT * FROM skills WHERE playerName LIKE '".fixName($name)."' LIMIT 1") or die(mysql_error());
	$row = mysql_fetch_array($query);
	$overall["lvl"] = getLevelForXP($row["Attackxp"]) + getLevelForXP($row["Defencexp"]) + getLevelForXP($row["Strengthxp"]) + getLevelForXP($row["Hitpointsxp"]) + getLevelForXP($row["Rangedxp"]) + getLevelForXP($row["Prayerxp"]) + getLevelForXP($row["Magicxp"]) + getLevelForXP($row["Cookingxp"]) + getLevelForXP($row["Woodcuttingxp"]) + getLevelForXP($row["Fletchingxp"]) + getLevelForXP($row["Fishingxp"]) + getLevelForXP($row["Firemakingxp"]) + getLevelForXP($row["Craftingxp"]) + getLevelForXP($row["Smithingxp"]) + getLevelForXP($row["Miningxp"]) + getLevelForXP($row["Herblorexp"]) + getLevelForXP($row["Agilityxp"]) + getLevelForXP($row["Thievingxp"]) + getLevelForXP($row["Slayerxp"]) + getLevelForXP($row["Farmingxp"]) + getLevelForXP($row["Runecraftxp"]);
	$overall["xp"] = $row["Attackxp"] + $row["Defencexp"] + $row["Strengthxp"] + $row["Hitpointsxp"] + $row["Rangedxp"] + $row["Prayerxp"] + $row["Magicxp"] + $row["Cookingxp"] + $row["Woodcuttingxp"] + $row["Fletchingxp"] + $row["Fishingxp"] + $row["Firemakingxp"] + $row["Craftingxp"] + $row["Smithingxp"] + $row["Miningxp"] + $row["Herblorexp"] + $row["Agilityxp"] + $row["Thievingxp"] + $row["Slayerxp"] + $row["Farmingxp"] + $row["Runecraftxp"];
	echo '<div id="player_header"><div id="player_sub" class="subsectionHeader">Personal Scores For &nbsp; <div class="player_name"><a href="'.$website.'/'.$pers.'?character='.$row["playerName"].'" target="_self">'.BBCode($row["playerName"]).'</a></div></div></div>
	<div id="PlayerSkill_back">
	<div id="scores_head" class="subsectionHeader" style="padding: 0px">Skills</div>
	<table width="100%" class="table_back" style="margin-bottom:5px" id="mini_player">
	<tr class="table_header">
	<th class="imageHead_P"></th>
	<th class="skillsHead_P">Skills</th>
	<th class="rankHead_P">Rank</th>
	<th class="levelHead_P">Level</th>
	<th class="xpHead_P">XP</th>
	</tr>
	<tr class="row rowp1">
	<td align="center"><img src="'.$website.$img_dir.'skill_icon_overall1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file.'" target="_self">Overall</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"0").'</td>
	<td class="alL">'.dots($overall["lvl"]).'</td>
	<td class="alL">'.dots($overall["xp"]).'</td>
	</tr>
	<tr class="row rowp2">
	<td align="center"><img src="'.$website.$img_dir.'skill_icon_attack1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=1" target="_self">Attack</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"1").'</td>
	<td class="alL">'.getLevelForXP($row["Attackxp"]).'</td>
	<td class="alL">'.dots($row["Attackxp"]).'</td>
	</tr>
	<tr class="row rowp3">
	<td align="center"><img src="'.$website.$img_dir.'skill_icon_defence1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=2" target="_self">Defence</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"2").'</td>
	<td class="alL">'.getLevelForXP($row["Defencexp"]).'</td>
	<td class="alL">'.dots($row["Defencexp"]).'</td>
	</tr>
	<tr class="row rowp4">
	<td align="center"><img src="'.$website.$img_dir.'skill_icon_strength1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=3" target="_self">Strength</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"3").'</td>
	<td class="alL">'.getLevelForXP($row["Strengthxp"]).'</td>
	<td class="alL">'.dots($row["Strengthxp"]).'</td>
	</tr>
	<tr class="row rowp5">
	<td align="center"><img src="'.$website.$img_dir.'skill_icon_hitpoints1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=4" target="_self">Hitpoints</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"4").'</td>
	<td class="alL">'.getLevelForXP($row["Hitpointsxp"]).'</td>
	<td class="alL">'.dots($row["Hitpointsxp"]).'</td>
	</tr>
	<tr class="row rowp6">
	<td align="center"><img src="'.$website.$img_dir.'skill_icon_range1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=5" target="_self">Ranged</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"5").'</td>
	<td class="alL">'.getLevelForXP($row["Rangedxp"]).'</td>
	<td class="alL">'.dots($row["Rangedxp"]).'</td>
	</tr>
	<tr class="row rowp7">
	<td align="center"><img src="'.$website.$img_dir.'skill_icon_prayer1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=6" target="_self">Prayer</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"6").'</td>
	<td class="alL">'.getLevelForXP($row["Prayerxp"]).'</td>
	<td class="alL">'.dots($row["Prayerxp"]).'</td>
	</tr>
	<tr class="row rowp8">
	<td align="center"><img src="'.$website.$img_dir.'skill_icon_magic1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=7" target="_self">Magic</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"7").'</td>
	<td class="alL">'.getLevelForXP($row["Magicxp"]).'</td>
	<td class="alL">'.dots($row["Magicxp"]).'</td>
	</tr>
	<tr class="row rowp9">
	<td align="center"><img src="'.$website.$img_dir.'skill_icon_cooking1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=8" target="_self">Cooking</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"8").'</td>
	<td class="alL">'.getLevelForXP($row["Cookingxp"]).'</td>
	<td class="alL">'.dots($row["Cookingxp"]).'</td>
	</tr>
	<tr class="row rowp10">
	<td align="center"><img src="'.$website.$img_dir.'skill_icon_woodcutting1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=9" target="_self">Woodcutting</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"9").'</td>
	<td class="alL">'.getLevelForXP($row["Woodcuttingxp"]).'</td>
	<td class="alL">'.dots($row["Woodcuttingxp"]).'</td>
	</tr>
	<tr class="row rowp11">
	<td align="center"><img src="'.$website.$img_dir.'skill_icon_fletching1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=10" target="_self">Fletching</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"10").'</td>
	<td class="alL">'.getLevelForXP($row["Fletchingxp"]).'</td>
	<td class="alL">'.dots($row["Fletchingxp"]).'</td>
	</tr>
	<tr class="row rowp12">
	<td align="center"><img src="'.$website.$img_dir.'skill_icon_fishing1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=11" target="_self">Fishing</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"11").'</td>
	<td class="alL">'.getLevelForXP($row["Fishingxp"]).'</td>
	<td class="alL">'.dots($row["Fishingxp"]).'</td>
	</tr>
	<tr class="row rowp13">
	<td align="center"><img src="'.$website.$img_dir.'skill_icon_firemaking1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=12" target="_self">Firemaking</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"12").'</td>
	<td class="alL">'.getLevelForXP($row["Firemakingxp"]).'</td>
	<td class="alL">'.dots($row["Firemakingxp"]).'</td>
	</tr>
	<tr class="row rowp14">
	<td align="center"><img src="'.$website.$img_dir.'skill_icon_crafting1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=13" target="_self">Crafting</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"13").'</td>
	<td class="alL">'.getLevelForXP($row["Craftingxp"]).'</td>
	<td class="alL">'.dots($row["Craftingxp"]).'</td>
	</tr>
	<tr class="row rowp15">
	<td align="center"><img src="'.$website.$img_dir.'skill_icon_smithing1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=14" target="_self">Smithing</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"14").'</td>
	<td class="alL">'.getLevelForXP($row["Smithingxp"]).'</td>
	<td class="alL">'.dots($row["Smithingxp"]).'</td>
	</tr>
	<tr class="row rowp16">
	<td align="center"><img src="'.$website.$img_dir.'skill_icon_mining1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=15" target="_self">Mining</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"15").'</td>
	<td class="alL">'.getLevelForXP($row["Miningxp"]).'</td>
	<td class="alL">'.dots($row["Miningxp"]).'</td>
	</tr>
	<tr class="row rowp17">
	<td align="center"><img src="'.$website.$img_dir.'skill_icon_herblore1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=16" target="_self">Herblore</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"16").'</td>
	<td class="alL">'.getLevelForXP($row["Herblorexp"]).'</td>
	<td class="alL">'.dots($row["Herblorexp"]).'</td>
	</tr>
	<tr class="row rowp18">
	<td align="center"><img src="'.$website.$img_dir.'skill_icon_agility1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=17" target="_self">Agility</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"17").'</td>
	<td class="alL">'.getLevelForXP($row["Agilityxp"]).'</td>
	<td class="alL">'.dots($row["Agilityxp"]).'</td>
	</tr>
	<tr class="row rowp19">
	<td align="center"><img src="'.$website.$img_dir.'skill_icon_thieving1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=18" target="_self">Thieving</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"18").'</td>
	<td class="alL">'.getLevelForXP($row["Thievingxp"]).'</td>
	<td class="alL">'.dots($row["Thievingxp"]).'</td>
	</tr>
	<tr class="row rowp20">
	<td align="center"><img src="'.$website.$img_dir.'skill_icon_slayer1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=19" target="_self">Slayer</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"19").'</td>
	<td class="alL">'.getLevelForXP($row["Slayerxp"]).'</td>
	<td class="alL">'.dots($row["Slayerxp"]).'</td>
	</tr>
	<tr class="row rowp21">
	<td align="center"><img src="'.$website.$img_dir.'skill_icon_farming1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=20" target="_self">Farming</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"20").'</td>
	<td class="alL">'.getLevelForXP($row["Farmingxp"]).'</td>
	<td class="alL">'.dots($row["Farmingxp"]).'</td>
	</tr>
	<tr class="row rowp22">
	<td align="center"><img src="'.$website.$img_dir.'skill_icon_runecraft1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=21" target="_self">Runecrafting</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"21").'</td>
	<td class="alL">'.getLevelForXP($row["Runecraftxp"]).'</td>
	<td class="alL">'.dots($row["Runecraftxp"]).'</td>
	</tr>
	</table>';
}
function showPlayerRank($rank) {
	$i=1;
	$query = mysql_query ("SELECT * FROM skillsoverall ORDER BY lvl DESC") or die(mysql_error());
	while($row = mysql_fetch_array($query)){
		if(fixName($row["playerName"]) == $player_rank) {
			if($i > $top_hiscore) {
				return "Not Ranked";
			} else {
				return $i;
			}
		}
		if ($i == $rank) {
			$player_rank = $row["playerName"];
		}
		$i++;
	}

	$query = mysql_query("SELECT * FROM skills WHERE playerName = '".$player_rank."' LIMIT 1") or die(mysql_error());
	
	$row = mysql_fetch_array($query);
	$overall["lvl"] = getLevelForXP($row["Attackxp"]) + getLevelForXP($row["Defencexp"]) + getLevelForXP($row["Strengthxp"]) + getLevelForXP($row["Hitpointsxp"]) + getLevelForXP($row["Rangedxp"]) + getLevelForXP($row["Prayerxp"]) + getLevelForXP($row["Magicxp"]) + getLevelForXP($row["Cookingxp"]) + getLevelForXP($row["Woodcuttingxp"]) + getLevelForXP($row["Fletchingxp"]) + getLevelForXP($row["Fishingxp"]) + getLevelForXP($row["Firemakingxp"]) + getLevelForXP($row["Craftingxp"]) + getLevelForXP($row["Smithingxp"]) + getLevelForXP($row["Miningxp"]) + getLevelForXP($row["Herblorexp"]) + getLevelForXP($row["Agilityxp"]) + getLevelForXP($row["Thievingxp"]) + getLevelForXP($row["Slayerxp"]) + getLevelForXP($row["Farmingxp"]) + getLevelForXP($row["Runecraftxp"]);
	$overall["xp"] = $row["Attackxp"] + $row["Defencexp"] + $row["Strengthxp"] + $row["Hitpointsxp"] + $row["Rangedxp"] + $row["Prayerxp"] + $row["Magicxp"] + $row["Cookingxp"] + $row["Woodcuttingxp"] + $row["Fletchingxp"] + $row["Fishingxp"] + $row["Firemakingxp"] + $row["Craftingxp"] + $row["Smithingxp"] + $row["Miningxp"] + $row["Herblorexp"] + $row["Agilityxp"] + $row["Thievingxp"] + $row["Slayerxp"] + $row["Farmingxp"] + $row["Runecraftxp"];
	echo '<div id="player_header"><div id="player_sub" class="subsectionHeader">Personal Scores For &nbsp; <div class="player_name"><a href="'.$website.'/'.$pers.'?character='.$row["playerName"].'" target="_self">'.BBCode($row["playerName"]).'</a></div></div></div>
	<div id="PlayerSkill_back">
	<div id="scores_head" class="subsectionHeader" style="padding: 0px">Skills</div>
	<table width="100%" class="table_back" style="margin-bottom:5px" id="mini_player">
	<tr class="table_header">
	<th class="imageHead_P"></th>
	<th class="skillsHead_P">Skills</th>
	<th class="rankHead_P">Rank</th>
	<th class="levelHead_P">Level</th>
	<th class="xpHead_P">XP</th>
	</tr>
	<tr class="row rowp1">
	<td align="center"><img src="'.$img_dir.'skill_icon_overall1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file.'" target="_self">Overall</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"0").'</td>
	<td class="alL">'.dots($overall["lvl"]).'</td>
	<td class="alL">'.dots($overall["xp"]).'</td>
	</tr>
	<tr class="row rowp2">
	<td align="center"><img src="'.$img_dir.'skill_icon_attack1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=1" target="_self">Attack</a></td>
	<td class="alL">'.findRank($player_rank,"1").'</td>
	<td class="alL">'.getLevelForXP($row["Attackxp"]).'</td>
	<td class="alL">'.dots($row["Attackxp"]).'</td>
	</tr>
	<tr class="row rowp3">
	<td align="center"><img src="'.$img_dir.'skill_icon_defence1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=2" target="_self">Defence</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"2").'</td>
	<td class="alL">'.getLevelForXP($row["Defencexp"]).'</td>
	<td class="alL">'.dots($row["Defencexp"]).'</td>
	</tr>
	<tr class="row rowp4">
	<td align="center"><img src="'.$img_dir.'skill_icon_strength1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=3" target="_self">Strength</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"3").'</td>
	<td class="alL">'.getLevelForXP($row["Strengthxp"]).'</td>
	<td class="alL">'.dots($row["Strengthxp"]).'</td>
	</tr>
	<tr class="row rowp5">
	<td align="center"><img src="'.$img_dir.'skill_icon_hitpoints1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=4" target="_self">Hitpoints</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"4").'</td>
	<td class="alL">'.getLevelForXP($row["Hitpointsxp"]).'</td>
	<td class="alL">'.dots($row["Hitpointsxp"]).'</td>
	</tr>
	<tr class="row rowp6">
	<td align="center"><img src="'.$img_dir.'skill_icon_range1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=5" target="_self">Ranged</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"5").'</td>
	<td class="alL">'.getLevelForXP($row["Rangedxp"]).'</td>
	<td class="alL">'.dots($row["Rangedxp"]).'</td>
	</tr>
	<tr class="row rowp7">
	<td align="center"><img src="'.$img_dir.'skill_icon_prayer1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=6" target="_self">Prayer</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"6").'</td>
	<td class="alL">'.getLevelForXP($row["Prayerxp"]).'</td>
	<td class="alL">'.dots($row["Prayerxp"]).'</td>
	</tr>
	<tr class="row rowp8">
	<td align="center"><img src="'.$img_dir.'skill_icon_magic1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=7" target="_self">Magic</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"7").'</td>
	<td class="alL">'.getLevelForXP($row["Magicxp"]).'</td>
	<td class="alL">'.dots($row["Magicxp"]).'</td>
	</tr>
	<tr class="row rowp9">
	<td align="center"><img src="'.$img_dir.'skill_icon_cooking1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=8" target="_self">Cooking</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"8").'</td>
	<td class="alL">'.getLevelForXP($row["Cookingxp"]).'</td>
	<td class="alL">'.dots($row["Cookingxp"]).'</td>
	</tr>
	<tr class="row rowp10">
	<td align="center"><img src="'.$img_dir.'skill_icon_woodcutting1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=9" target="_self">Woodcutting</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"9").'</td>
	<td class="alL">'.getLevelForXP($row["Woodcuttingxp"]).'</td>
	<td class="alL">'.dots($row["Woodcuttingxp"]).'</td>
	</tr>
	<tr class="row rowp11">
	<td align="center"><img src="'.$img_dir.'skill_icon_fletching1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=10" target="_self">Fletching</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"10").'</td>
	<td class="alL">'.getLevelForXP($row["Fletchingxp"]).'</td>
	<td class="alL">'.dots($row["Fletchingxp"]).'</td>
	</tr>
	<tr class="row rowp12">
	<td align="center"><img src="'.$img_dir.'skill_icon_fishing1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=11" target="_self">Fishing</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"11").'</td>
	<td class="alL">'.getLevelForXP($row["Fishingxp"]).'</td>
	<td class="alL">'.dots($row["Fishingxp"]).'</td>
	</tr>
	<tr class="row rowp13">
	<td align="center"><img src="'.$img_dir.'skill_icon_firemaking1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=12" target="_self">Firemaking</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"12").'</td>
	<td class="alL">'.getLevelForXP($row["Firemakingxp"]).'</td>
	<td class="alL">'.dots($row["Firemakingxp"]).'</td>
	</tr>
	<tr class="row rowp14">
	<td align="center"><img src="'.$img_dir.'skill_icon_crafting1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=13" target="_self">Crafting</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"13").'</td>
	<td class="alL">'.getLevelForXP($row["Craftingxp"]).'</td>
	<td class="alL">'.dots($row["Craftingxp"]).'</td>
	</tr>
	<tr class="row rowp15">
	<td align="center"><img src="'.$img_dir.'skill_icon_smithing1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=14" target="_self">Smithing</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"14").'</td>
	<td class="alL">'.getLevelForXP($row["Smithingxp"]).'</td>
	<td class="alL">'.dots($row["Smithingxp"]).'</td>
	</tr>
	<tr class="row rowp16">
	<td align="center"><img src="'.$img_dir.'skill_icon_mining1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=15" target="_self">Mining</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"15").'</td>
	<td class="alL">'.getLevelForXP($row["Miningxp"]).'</td>
	<td class="alL">'.dots($row["Miningxp"]).'</td>
	</tr>
	<tr class="row rowp17">
	<td align="center"><img src="'.$img_dir.'skill_icon_herblore1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=16" target="_self">Herblore</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"16").'</td>
	<td class="alL">'.getLevelForXP($row["Herblorexp"]).'</td>
	<td class="alL">'.dots($row["Herblorexp"]).'</td>
	</tr>
	<tr class="row rowp18">
	<td align="center"><img src="'.$img_dir.'skill_icon_agility1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=17" target="_self">Agility</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"17").'</td>
	<td class="alL">'.getLevelForXP($row["Agilityxp"]).'</td>
	<td class="alL">'.dots($row["Agilityxp"]).'</td>
	</tr>
	<tr class="row rowp19">
	<td align="center"><img src="'.$img_dir.'skill_icon_thieving1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=18" target="_self">Thieving</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"18").'</td>
	<td class="alL">'.getLevelForXP($row["Thievingxp"]).'</td>
	<td class="alL">'.dots($row["Thievingxp"]).'</td>
	</tr>
	<tr class="row rowp20">
	<td align="center"><img src="'.$img_dir.'skill_icon_slayer1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=19" target="_self">Slayer</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"19").'</td>
	<td class="alL">'.getLevelForXP($row["Slayerxp"]).'</td>
	<td class="alL">'.dots($row["Slayerxp"]).'</td>
	</tr>
	<tr class="row rowp21">
	<td align="center"><img src="'.$img_dir.'skill_icon_farming1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=20" target="_self">Farming</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"20").'</td>
	<td class="alL">'.getLevelForXP($row["Farmingxp"]).'</td>
	<td class="alL">'.dots($row["Farmingxp"]).'</td>
	</tr>
	<tr class="row rowp22">
	<td align="center"><img src="'.$img_dir.'skill_icon_runecraft1.gif"></td>
	<td class="alL"><a href="'.$website.'/'.$file2.'?type=21" target="_self">Runecrafting</a></td>
	<td class="alL" style="font-weight: normal;">'.findRank($row['playerName'],"21").'</td>
	<td class="alL">'.getLevelForXP($row["Runecraftxp"]).'</td>
	<td class="alL">'.dots($row["Runecraftxp"]).'</td>
	</tr>
	</table>
	</div>';
}
function showComparePlayers($user1,$user2) {
	$i=1;

	$user1_query = mysql_query("SELECT * FROM skills WHERE playerName = '".$user1."' LIMIT 1") or die(mysql_error());
	$user2_query = mysql_query("SELECT * FROM skills WHERE playerName = '".$user2."' LIMIT 1") or die(mysql_error());
	
	$user1 = mysql_fetch_array($user1_query);
	$user2 = mysql_fetch_array($user2_query);
	$overall["lvl"] = getLevelForXP($user1["Attackxp"]) + getLevelForXP($user1["Defencexp"]) + getLevelForXP($user1["Strengthxp"]) + getLevelForXP($user1["Hitpointsxp"]) + getLevelForXP($user1["Rangedxp"]) + getLevelForXP($user1["Prayerxp"]) + getLevelForXP($user1["Magicxp"]) + getLevelForXP($user1["Cookingxp"]) + getLevelForXP($user1["Woodcuttingxp"]) + getLevelForXP($user1["Fletchingxp"]) + getLevelForXP($user1["Fishingxp"]) + getLevelForXP($user1["Firemakingxp"]) + getLevelForXP($user1["Craftingxp"]) + getLevelForXP($user1["Smithingxp"]) + getLevelForXP($user1["Miningxp"]) + getLevelForXP($user1["Herblorexp"]) + getLevelForXP($user1["Agilityxp"]) + getLevelForXP($user1["Thievingxp"]) + getLevelForXP($user1["Slayerxp"]) + getLevelForXP($user1["Farmingxp"]) + getLevelForXP($user1["Runecraftxp"]);
	$overall["xp"] = $user1["Attackxp"] + $user1["Defencexp"] + $user1["Strengthxp"] + $user1["Hitpointsxp"] + $user1["Rangedxp"] + $user1["Prayerxp"] + $user1["Magicxp"] + $user1["Cookingxp"] + $user1["Woodcuttingxp"] + $user1["Fletchingxp"] + $user1["Fishingxp"] + $user1["Firemakingxp"] + $user1["Craftingxp"] + $user1["Smithingxp"] + $user1["Miningxp"] + $user1["Herblorexp"] + $user1["Agilityxp"] + $user1["Thievingxp"] + $user1["Slayerxp"] + $user1["Farmingxp"] + $user1["Runecraftxp"];
	$overall2["lvl"] = getLevelForXP($user2["Attackxp"]) + getLevelForXP($user2["Defencexp"]) + getLevelForXP($user2["Strengthxp"]) + getLevelForXP($user2["Hitpointsxp"]) + getLevelForXP($user2["Rangedxp"]) + getLevelForXP($user2["Prayerxp"]) + getLevelForXP($user2["Magicxp"]) + getLevelForXP($user2["Cookingxp"]) + getLevelForXP($user2["Woodcuttingxp"]) + getLevelForXP($user2["Fletchingxp"]) + getLevelForXP($user2["Fishingxp"]) + getLevelForXP($user2["Firemakingxp"]) + getLevelForXP($user2["Craftingxp"]) + getLevelForXP($user2["Smithingxp"]) + getLevelForXP($user2["Miningxp"]) + getLevelForXP($user2["Herblorexp"]) + getLevelForXP($user2["Agilityxp"]) + getLevelForXP($user2["Thievingxp"]) + getLevelForXP($user2["Slayerxp"]) + getLevelForXP($user2["Farmingxp"]) + getLevelForXP($user2["Runecraftxp"]);
	$overall2["xp"] = $user2["Attackxp"] + $user2["Defencexp"] + $user2["Strengthxp"] + $user2["Hitpointsxp"] + $user2["Rangedxp"] + $user2["Prayerxp"] + $user2["Magicxp"] + $user2["Cookingxp"] + $user2["Woodcuttingxp"] + $user2["Fletchingxp"] + $user2["Fishingxp"] + $user2["Firemakingxp"] + $user2["Craftingxp"] + $user2["Smithingxp"] + $user2["Miningxp"] + $user2["Herblorexp"] + $user2["Agilityxp"] + $user2["Thievingxp"] + $user2["Slayerxp"] + $user2["Farmingxp"] + $user2["Runecraftxp"];
	echo '<table width="742" cellpadding="2" cellspacing="0">';
	echo '<tr>';
	echo '<td width="376"><div id="player_header" class="buser1n_box" style="width: 100%;"><div id="player_sub" class="subsectionHeader">Comparing '.$user1['playerName'].' with '.$user2['playerName'].'</div></div></td>';
	echo '<td width="376"><div id="player_header" class="buser1n_box" style="width: 100%;"><div id="player_sub" class="subsectionHeader"><form action="compare.php" method="get" style="padding: 0px;"><input class="textinput text" maxlength="12" type="text" name="user1" value="'.$user1['playerName'].'" id="user1" style="width: 100px; height: 15px;" /> with <input class="textinput text" maxlength="12" type="text" name="user2" value="'.$user2['playerName'].'" id="user2" style="width: 100px; height: 15px;" /> <input class="buttonmedium" type="submit" value="Compare"></form></div></div></td>';
	echo '</tr>';
	echo '<tr class="table_header">';
	echo '<td colspan="2" height="30">';
	echo '	<table width="100%" cellpadding="2" cellspacing="0">';
	echo '	<tr>';
	echo '	<td class="subsectionHeader" height="30" style="width: 50%; background: url(\'/\');">'.$user1['playerName'].'</td>';
	echo '	<td><img src="images/skills_header.gif" /></td>';
	echo '	<td class="subsectionHeader" height="30" style="width: 50%; background: url(\'/\');">'.$user2['playerName'].'</td>';
	echo '	</tr>';
	echo '	</table>';
	echo '</td>';
	echo '</tr>';
	echo '<tr>';
	echo '<td valign="top">';
	echo '<div id="PlayerSkill_back" class="buser1n_box" style="width: 100%;">';
	echo '	<table width="100%" class="table_back" style="margin-bottom:5px" id="mini_player">';
	echo '	<tr class="table_header">';
	echo '	<th class="imageHead_P"></th>';
	echo '	<th class="skillsHead_P">Skills</th>';
	echo '	<th class="rankHead_P">Rank</th>';
	echo '	<th class="levelHead_P">Level</th>';
	echo '	<th class="xpHead_P">XP</th>';
	echo '	<th class="imageHead_P"></th>';
	echo '	</tr>';
	if ($_GET['user1'] != "") {
	echo '	<tr class="row rowp1">';
	echo '	<td align="center"><img src="images/skill_icon_overall1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file.'" target="_self">Overall</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user1['playerName'],"0").'</td>';
	echo '	<td class="alL">'.dots($overall["lvl"]).'</td>';
	echo '	<td class="alL">'.dots($overall["xp"]).'</td>';
	echo '	<td class="alL">'.getCompare("0",$user1['playerName'],$user2['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp2">';
	echo '	<td align="center"><img src="images/skill_icon_attack1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=1" target="_self">Attack</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user1['playerName'],"1").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user1["Attackxp"]).'</td>';
	echo '	<td class="alL">'.dots($user1["Attackxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("1",$user1['playerName'],$user2['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp3">';
	echo '	<td align="center"><img src="images/skill_icon_defence1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=2" target="_self">Defence</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user1['playerName'],"2").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user1["Defencexp"]).'</td>';
	echo '	<td class="alL">'.dots($user1["Defencexp"]).'</td>';
	echo '	<td class="alL">'.getCompare("2",$user1['playerName'],$user2['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp4">';
	echo '	<td align="center"><img src="images/skill_icon_strength1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=3" target="_self">Strength</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user1['playerName'],"3").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user1["Strengthxp"]).'</td>';
	echo '	<td class="alL">'.dots($user1["Strengthxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("3",$user1['playerName'],$user2['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp5">';
	echo '	<td align="center"><img src="images/skill_icon_hitpoints1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=4" target="_self">Hitpoints</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user1['playerName'],"4").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user1["Hitpointsxp"]).'</td>';
	echo '	<td class="alL">'.dots($user1["Hitpointsxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("4",$user1['playerName'],$user2['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp6">';
	echo '	<td align="center"><img src="images/skill_icon_range1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=5" target="_self">Ranged</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user1['playerName'],"5").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user1["Rangedxp"]).'</td>';
	echo '	<td class="alL">'.dots($user1["Rangedxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("5",$user1['playerName'],$user2['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp7">';
	echo '	<td align="center"><img src="images/skill_icon_prayer1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=6" target="_self">Prayer</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user1['playerName'],"6").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user1["Prayerxp"]).'</td>';
	echo '	<td class="alL">'.dots($user1["Prayerxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("6",$user1['playerName'],$user2['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp8">';
	echo '	<td align="center"><img src="images/skill_icon_magic1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=7" target="_self">Magic</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user1['playerName'],"7").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user1["Magicxp"]).'</td>';
	echo '	<td class="alL">'.dots($user1["Magicxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("7",$user1['playerName'],$user2['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp9">';
	echo '	<td align="center"><img src="images/skill_icon_cooking1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=8" target="_self">Cooking</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user1['playerName'],"8").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user1["Cookingxp"]).'</td>';
	echo '	<td class="alL">'.dots($user1["Cookingxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("8",$user1['playerName'],$user2['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp10">';
	echo '	<td align="center"><img src="images/skill_icon_woodcutting1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=9" target="_self">Woodcutting</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user1['playerName'],"9").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user1["Woodcuttingxp"]).'</td>';
	echo '	<td class="alL">'.dots($user1["Woodcuttingxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("9",$user1['playerName'],$user2['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp11">';
	echo '	<td align="center"><img src="images/skill_icon_fletching1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=10" target="_self">Fletching</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user1['playerName'],"10").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user1["Fletchingxp"]).'</td>';
	echo '	<td class="alL">'.dots($user1["Fletchingxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("10",$user1['playerName'],$user2['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp12">';
	echo '	<td align="center"><img src="images/skill_icon_fishing1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=11" target="_self">Fishing</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user1['playerName'],"11").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user1["Fishingxp"]).'</td>';
	echo '	<td class="alL">'.dots($user1["Fishingxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("11",$user1['playerName'],$user2['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp13">';
	echo '	<td align="center"><img src="images/skill_icon_firemaking1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=12" target="_self">Firemaking</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user1['playerName'],"12").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user1["Firemakingxp"]).'</td>';
	echo '	<td class="alL">'.dots($user1["Firemakingxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("12",$user1['playerName'],$user2['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp14">';
	echo '	<td align="center"><img src="images/skill_icon_crafting1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=13" target="_self">Crafting</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user1['playerName'],"13").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user1["Craftingxp"]).'</td>';
	echo '	<td class="alL">'.dots($user1["Craftingxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("13",$user1['playerName'],$user2['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp15">';
	echo '	<td align="center"><img src="images/skill_icon_smithing1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=14" target="_self">Smithing</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user1['playerName'],"14").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user1["Smithingxp"]).'</td>';
	echo '	<td class="alL">'.dots($user1["Smithingxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("14",$user1['playerName'],$user2['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp16">';
	echo '	<td align="center"><img src="images/skill_icon_mining1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=15" target="_self">Mining</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user1['playerName'],"15").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user1["Miningxp"]).'</td>';
	echo '	<td class="alL">'.dots($user1["Miningxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("15",$user1['playerName'],$user2['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp17">';
	echo '	<td align="center"><img src="images/skill_icon_herblore1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=16" target="_self">Herblore</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user1['playerName'],"16").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user1["Herblorexp"]).'</td>';
	echo '	<td class="alL">'.dots($user1["Herblorexp"]).'</td>';
	echo '	<td class="alL">'.getCompare("16",$user1['playerName'],$user2['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp18">';
	echo '	<td align="center"><img src="images/skill_icon_agility1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=17" target="_self">Agility</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user1['playerName'],"17").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user1["Agilityxp"]).'</td>';
	echo '	<td class="alL">'.dots($user1["Agilityxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("17",$user1['playerName'],$user2['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp19">';
	echo '	<td align="center"><img src="images/skill_icon_thieving1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=18" target="_self">Thieving</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user1['playerName'],"18").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user1["Thievingxp"]).'</td>';
	echo '	<td class="alL">'.dots($user1["Thievingxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("18",$user1['playerName'],$user2['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp20">';
	echo '	<td align="center"><img src="images/skill_icon_slayer1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=19" target="_self">Slayer</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user1['playerName'],"19").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user1["Slayerxp"]).'</td>';
	echo '	<td class="alL">'.dots($user1["Slayerxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("19",$user1['playerName'],$user2['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp21">';
	echo '	<td align="center"><img src="images/skill_icon_farming1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=20" target="_self">Farming</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user1['playerName'],"20").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user1["Farmingxp"]).'</td>';
	echo '	<td class="alL">'.dots($user1["Farmingxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("20",$user1['playerName'],$user2['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp22">';
	echo '	<td align="center"><img src="images/skill_icon_runecraft1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=21" target="_self">Runecrafting</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user1['playerName'],"21").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user1["Runecraftxp"]).'</td>';
	echo '	<td class="alL">'.dots($user1["Runecraftxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("21",$user1['playerName'],$user2['playerName']).'</td>';
	echo '	</tr>';
	} else {
	echo '	<tr class="row rowp1">';
	echo '	<td colspan="5" align="center" style="padding: 10px;">Please enter a username to compare...</td>';
	echo '	</tr>';
	}
	echo '	</table>';
	echo '</div>';
	echo '</td>';
	echo '<td valign="top">';
	echo '<div id="PlayerSkill_back" class="buser1n_box" style="width: 100%;">';
	echo '	<table width="100%" class="table_back" style="margin-bottom:5px" id="mini_player">';
	echo '	<tr class="table_header">';
	echo '	<th class="imageHead_P"></th>';
	echo '	<th class="skillsHead_P">Skills</th>';
	echo '	<th class="rankHead_P">Rank</th>';
	echo '	<th class="levelHead_P">Level</th>';
	echo '	<th class="xpHead_P">XP</th>';
	echo '	<th class="imageHead_P"></th>';
	echo '	</tr>';
	if ($_GET['user2'] != "") {
	echo '	<tr class="row rowp1">';
	echo '	<td align="center"><img src="images/skill_icon_overall1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file.'" target="_self">Overall</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user2['playerName'],"0").'</td>';
	echo '	<td class="alL">'.dots($overall2["lvl"]).'</td>';
	echo '	<td class="alL">'.dots($overall2["xp"]).'</td>';
	echo '	<td class="alL">'.getCompare("0",$user2['playerName'],$user1['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp2">';
	echo '	<td align="center"><img src="images/skill_icon_attack1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=1" target="_self">Attack</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user2['playerName'],"1").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user2["Attackxp"]).'</td>';
	echo '	<td class="alL">'.dots($user2["Attackxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("1",$user2['playerName'],$user1['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp3">';
	echo '	<td align="center"><img src="images/skill_icon_defence1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=2" target="_self">Defence</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user2['playerName'],"2").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user2["Defencexp"]).'</td>';
	echo '	<td class="alL">'.dots($user2["Defencexp"]).'</td>';
	echo '	<td class="alL">'.getCompare("2",$user2['playerName'],$user1['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp4">';
	echo '	<td align="center"><img src="images/skill_icon_strength1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=3" target="_self">Strength</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user2['playerName'],"3").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user2["Strengthxp"]).'</td>';
	echo '	<td class="alL">'.dots($user2["Strengthxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("3",$user2['playerName'],$user1['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp5">';
	echo '	<td align="center"><img src="images/skill_icon_hitpoints1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=4" target="_self">Hitpoints</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user2['playerName'],"4").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user2["Hitpointsxp"]).'</td>';
	echo '	<td class="alL">'.dots($user2["Hitpointsxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("4",$user2['playerName'],$user1['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp6">';
	echo '	<td align="center"><img src="images/skill_icon_range1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=5" target="_self">Ranged</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user2['playerName'],"5").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user2["Rangedxp"]).'</td>';
	echo '	<td class="alL">'.dots($user2["Rangedxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("5",$user2['playerName'],$user1['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp7">';
	echo '	<td align="center"><img src="images/skill_icon_prayer1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=6" target="_self">Prayer</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user2['playerName'],"6").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user2["Prayerxp"]).'</td>';
	echo '	<td class="alL">'.dots($user2["Prayerxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("6",$user2['playerName'],$user1['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp8">';
	echo '	<td align="center"><img src="images/skill_icon_magic1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=7" target="_self">Magic</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user2['playerName'],"7").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user2["Magicxp"]).'</td>';
	echo '	<td class="alL">'.dots($user2["Magicxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("7",$user2['playerName'],$user1['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp9">';
	echo '	<td align="center"><img src="images/skill_icon_cooking1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=8" target="_self">Cooking</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user2['playerName'],"8").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user2["Cookingxp"]).'</td>';
	echo '	<td class="alL">'.dots($user2["Cookingxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("8",$user2['playerName'],$user1['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp10">';
	echo '	<td align="center"><img src="images/skill_icon_woodcutting1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=9" target="_self">Woodcutting</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user2['playerName'],"9").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user2["Woodcuttingxp"]).'</td>';
	echo '	<td class="alL">'.dots($user2["Woodcuttingxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("9",$user2['playerName'],$user1['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp11">';
	echo '	<td align="center"><img src="images/skill_icon_fletching1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=10" target="_self">Fletching</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user2['playerName'],"10").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user2["Fletchingxp"]).'</td>';
	echo '	<td class="alL">'.dots($user2["Fletchingxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("10",$user2['playerName'],$user1['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp12">';
	echo '	<td align="center"><img src="images/skill_icon_fishing1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=11" target="_self">Fishing</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user2['playerName'],"11").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user2["Fishingxp"]).'</td>';
	echo '	<td class="alL">'.dots($user2["Fishingxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("11",$user2['playerName'],$user1['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp13">';
	echo '	<td align="center"><img src="images/skill_icon_firemaking1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=12" target="_self">Firemaking</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user2['playerName'],"12").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user2["Firemakingxp"]).'</td>';
	echo '	<td class="alL">'.dots($user2["Firemakingxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("12",$user2['playerName'],$user1['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp14">';
	echo '	<td align="center"><img src="images/skill_icon_crafting1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=13" target="_self">Crafting</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user2['playerName'],"13").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user2["Craftingxp"]).'</td>';
	echo '	<td class="alL">'.dots($user2["Craftingxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("13",$user2['playerName'],$user1['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp15">';
	echo '	<td align="center"><img src="images/skill_icon_smithing1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=14" target="_self">Smithing</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user2['playerName'],"14").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user2["Smithingxp"]).'</td>';
	echo '	<td class="alL">'.dots($user2["Smithingxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("14",$user2['playerName'],$user1['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp16">';
	echo '	<td align="center"><img src="images/skill_icon_mining1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=15" target="_self">Mining</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user2['playerName'],"15").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user2["Miningxp"]).'</td>';
	echo '	<td class="alL">'.dots($user2["Miningxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("15",$user2['playerName'],$user1['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp17">';
	echo '	<td align="center"><img src="images/skill_icon_herblore1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=16" target="_self">Herblore</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user2['playerName'],"16").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user2["Herblorexp"]).'</td>';
	echo '	<td class="alL">'.dots($user2["Herblorexp"]).'</td>';
	echo '	<td class="alL">'.getCompare("16",$user2['playerName'],$user1['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp18">';
	echo '	<td align="center"><img src="images/skill_icon_agility1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=17" target="_self">Agility</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user2['playerName'],"17").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user2["Agilityxp"]).'</td>';
	echo '	<td class="alL">'.dots($user2["Agilityxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("17",$user2['playerName'],$user1['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp19">';
	echo '	<td align="center"><img src="images/skill_icon_thieving1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=18" target="_self">Thieving</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user2['playerName'],"18").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user2["Thievingxp"]).'</td>';
	echo '	<td class="alL">'.dots($user2["Thievingxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("18",$user2['playerName'],$user1['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp20">';
	echo '	<td align="center"><img src="images/skill_icon_slayer1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=19" target="_self">Slayer</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user2['playerName'],"19").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user2["Slayerxp"]).'</td>';
	echo '	<td class="alL">'.dots($user2["Slayerxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("19",$user2['playerName'],$user1['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp21">';
	echo '	<td align="center"><img src="images/skill_icon_farming1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=20" target="_self">Farming</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user2['playerName'],"20").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user2["Farmingxp"]).'</td>';
	echo '	<td class="alL">'.dots($user2["Farmingxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("20",$user2['playerName'],$user1['playerName']).'</td>';
	echo '	</tr>';
	echo '	<tr class="row rowp22">';
	echo '	<td align="center"><img src="images/skill_icon_runecraft1.gif"></td>';
	echo '	<td class="alL"><a href="'.$website.'/'.$file2.'?type=21" target="_self">Runecrafting</a></td>';
	echo '	<td class="alL" style="font-weight: normal;">'.findRank($user2['playerName'],"21").'</td>';
	echo '	<td class="alL">'.getLevelForXP($user2["Runecraftxp"]).'</td>';
	echo '	<td class="alL">'.dots($user2["Runecraftxp"]).'</td>';
	echo '	<td class="alL">'.getCompare("21",$user2['playerName'],$user1['playerName']).'</td>';
	echo '	</tr>';
	} else {
	echo '	<tr class="row rowp1">';
	echo '	<td colspan="5" align="center" style="padding: 10px;">Please enter a username to compare...</td>';
	echo '	</tr>';
	}
	echo '	</table>';
	echo '</div>';
	echo '</td>';
	echo '</tr>';
	echo '</table>';
}
?>