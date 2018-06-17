                    </td>
                    <?php if ($showRight == "false") { } else { ?>
                    <td width="50" valign="top">
                    <div id="search_back" style="padding-bottom: 0px;">
                        <div class="subsectionHeader search_title">Search By Name</div>
                        <div class="search_small" style="padding-top: 10px;">
                        <form action="personal.php" method="get" style="padding: 0px;" onsubmit="if(document.getElementById('character').value) { } else { alert('Please write a username'); }">
                        <input class="textinput text" maxlength="12" type="text" name="character" id="character">
                        <div style="height: 10px;"></div>
                        <input class="buttonmedium" type="submit" value="Search" onclick="if(document.getElementById('character').value) { location.href = 'personal.php?character='+document.getElementById('character').value; } else { alert('Please write a username'); }">
                        </form>
                        </div>
                        
                        <div class="subsectionHeader search_title">Search By Rank</div>
                        <div class="search_small" style="padding-top: 10px;">
                        <form action="rank.php" method="get" style="padding: 0px;" onsubmit="if(document.getElementById('rank').value) { } else { alert('Please enter a number'); }">
                        <input class="textinput text" maxlength="12" type="text" name="rank" id="rank">
                        <div style="height: 10px;"></div>
                        <input class="buttonmedium" type="submit" value="Search" onclick="if(document.getElementById('rank').value) { location.href = 'rank.php?rank='+document.getElementById('rank').value; } else { alert('Please enter a number'); }">
                        </form>
                        </div>
                        
                        <div class="subsectionHeader search_title">Compare Users</div>
                        <div class="search_large" style="padding-top: 10px;">
                        <form action="compare.php" method="get" style="padding: 0px;">
                        <input class="textinput text" maxlength="12" type="text" name="user1" id="user1">
                        <div style="height: 5px;"></div>
                        <input class="textinput text" maxlength="12" type="text" name="user2" id="user2">
                        <div style="height: 10px;"></div>
                        <input class="buttonmedium" type="submit" value="Compare">
                        </form>
                        </div>
                        
                        <div class="subsectionHeader search_title">Forums</div>
                        <div class="search_small" style="padding-top: 10px;">
                        To Go To The<br>Forums<br>
                        <form action="/forums/" method="" class="no_margin">
                        <input id="search_friends" type="submit" class="buttonmedium" value="Click Here">
                        </form>
                        </div>
                    </td>
                    <?php } ?>
                    </tr>
                    </table>
            	</td>
                </tr>
                </table>
            </td>
            </tr>
            </tbody>
            </table>
            </div>
        </div>
    </div>
</td>

</body>
</html>