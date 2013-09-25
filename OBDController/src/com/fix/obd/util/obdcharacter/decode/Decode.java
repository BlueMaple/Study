package com.fix.obd.util.obdcharacter.decode;

import java.util.ArrayList;

import com.fix.obd.util.model.CharacterIterator;

public interface Decode {
	public void print(CharacterIterator cha);
	public void DBOperation(CharacterIterator cha);
}
