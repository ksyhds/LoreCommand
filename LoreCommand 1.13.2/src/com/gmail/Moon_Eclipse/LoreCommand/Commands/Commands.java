package com.gmail.Moon_Eclipse.LoreCommand.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.gmail.Moon_Eclipse.LoreCommand.LoreCommand;

public class Commands implements CommandExecutor
{
	// 메인 플러그인 클래스의 인스턴스를 저장할 변수를 선
	LoreCommand plugin;
	
	// 커맨드 클래스의 생성자 작성
	public Commands(LoreCommand instance)
	{
		// 클래스 생성과 동시에 메인 플러그인의 인스턴스를 내부 변수에 저장
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sneder, Command command, String Label, String[] args)
	{
		return true;
	}
	
}
