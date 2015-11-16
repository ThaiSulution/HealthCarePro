function tabClick(element)
{
    if(element.className == 'tabSelected')
        return;
        
    $(".tabSelected").addClass('tab');
    $(".tabSelected").get(0).style.zIndex = parseInt($(".tabSelected").get(0).style.zIndex) -  200;
    $(".tabSelected").removeClass('tabSelected');
    
    element.className = 'tabSelected'; 
    element.style.zIndex = parseInt(element.style.zIndex) + 200;

    $(".tabContent").hide();
    
    var collection = $("#PillTabListLeft").children();
   
    var direction = 1;
    var zindex = 1;
    for(var i=0;i<collection.length;i++)
    {
        if(collection[i].style.zIndex > 200)
            {
                zindex += 10;
                direction = -1;
            }
           else
           {
                collection[i].style.zIndex = zindex += direction;
           }
    }
    
    switch(element.id)
    {
        case "tabPill":    
            //$("#PillContent").fadeIn("fast");
            $("#PillContent").show();
            break;
        case "tabPillExclamation":
             //$("#PillExclamationContent").fadeIn("fast");   
            $("#PillExclamationContent").show();
            break;
        case "tabPillDelete":
            //$("#PillDeleteContent").fadeIn("fast");       
            $("#PillDeleteContent").show();
            break;
        case "tabPillPrice":
            //$("#PillPriceContent").fadeIn("fast");       
            $("#PillPriceContent").show();
            break;
        case "tabPillBuy":
            //$("#PillBuyContent").fadeIn("fast");       
            $("#PillBuyContent").show();
            break;
        default:
        break;
    }
}