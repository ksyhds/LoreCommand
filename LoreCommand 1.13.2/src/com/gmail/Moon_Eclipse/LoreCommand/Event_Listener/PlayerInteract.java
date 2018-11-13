package com.gmail.Moon_Eclipse.LoreCommand.Event_Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteract implements Listener
{
	@EventHandler
	public void onClick(PlayerInteractEvent event)
	{
		// 이벤트를 발생시킨 플레이어를 저장
		Player player = event.getPlayer();
		
		// 플레이어의 손에 있는 아이템을 얻어와 저장
		ItemStack ItemInMainHand = player.getEquipment().getItemInMainHand();
		
		// 만약 아이템에 정보가 없거나 로어가 없다면
		if(!ItemInMainHand.hasItemMeta() || !ItemInMainHand.getItemMeta().hasLore())
		{
			// 중지
			return;
		}
		
		
		// 만약 빈손이 아니고 공중에 좌클릭이거나 블럭에 좌클릭일 경우
		if(
				!(ItemInMainHand.getType().toString().equals("AIR"))
				&& ( event.getAction().equals(Action.LEFT_CLICK_AIR)
				||   event.getAction().equals(Action.LEFT_CLICK_BLOCK))
		  )
		{
			
		}
		
		// 만약 빈손이 아니고 공중에 우클릭이거나 블럭에 우클릭일 경우
		if(
				!(ItemInMainHand.getType().toString().equals("AIR"))
				&& ( event.getAction().equals(Action.RIGHT_CLICK_AIR)
				||   event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
		  )
		{
			
		}
	}
}
