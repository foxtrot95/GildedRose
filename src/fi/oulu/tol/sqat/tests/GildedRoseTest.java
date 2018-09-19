package fi.oulu.tol.sqat.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fi.oulu.tol.sqat.GildedRose;
import fi.oulu.tol.sqat.Item;

public class GildedRoseTest {

// Example scenarios for testing
//   Item("+5 Dexterity Vest", 10, 20));
//   Item("Aged Brie", 2, 0));
//   Item("Elixir of the Mongoose", 5, 7));
//   Item("Sulfuras, Hand of Ragnaros", 0, 80));
//   Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
//   Item("Conjured Mana Cake", 3, 6));

	// 
	@Test
	public void testUpdateEndOfDay_AgedBrie_Quality_10_11() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Aged Brie", 2, 10) );
		
		// Act
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemBrie = items.get(0);
		assertEquals("Quality should increase", 11, itemBrie.getQuality());
	}
    
	@Test
	public void testUpdateEndOfDay_NormalItemQualityDecrease_Quality_6_5() {
		// Arrange 
		GildedRose store = new GildedRose();
		store.addItem(new Item("Conjured Mana Cake", 3, 6));
		
		//Act 
		store.updateEndOfDay();
		
		//Assert 
		List<Item> items = store.getItems(); 
		Item conjuredManaCake = items.get(0); 
		assertEquals("Quality should decrease", 5, conjuredManaCake.getQuality()); 
	}
	
	@Test
	public void testUpdateEndOfDay_NormalItemSellInDecrease_SellIn_3_2() {
		// Arrange 
		GildedRose store = new GildedRose();
		store.addItem(new Item("Conjured Mana Cake", 3, 6));
		
		//Act 
		store.updateEndOfDay();
		
		//Assert 
		List<Item> items = store.getItems(); 
		Item conjuredManaCake = items.get(0); 
		assertEquals("Sell By Date should decrease", 2, conjuredManaCake.getSellIn()); 
	}
	
	@Test
	public void testUpdateEndOfDay_SellByDatePassed_NormalItem_Quality_6_4() {
		// Arrange 
		GildedRose store = new GildedRose();
		store.addItem(new Item("Conjured Mana Cake", 0, 6));
		
		//Act 
		store.updateEndOfDay();
		
		//Assert 
		List<Item> items = store.getItems(); 
		Item conjuredManaCake = items.get(0); 
		assertEquals("Quality should degrade twice as fast", 4, conjuredManaCake.getQuality()); 
	}
	
	@Test
	public void testUpdateEndOfDay_QualityNotNegative_NormalItem_Quality_0_0() {
		// Arrange 
		GildedRose store = new GildedRose();
		store.addItem(new Item("Conjured Mana Cake", 3, 0));
		
		//Act 
		store.updateEndOfDay();
		
		//Assert 
		List<Item> items = store.getItems(); 
		Item conjuredManaCake = items.get(0); 
		assertEquals("Quality should not be negative", 0, conjuredManaCake.getQuality()); 
	}
	
	@Test
	public void testUpdateEndOfDay_NotOver50_AgedBrie_Quality_50_50() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Aged Brie", 2, 50) );
		
		// Act
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemBrie = items.get(0);
		assertEquals("Quality should not increase over 50", 50, itemBrie.getQuality());
	}
	
	@Test
	public void testUpdateEndOfDay_SellByDatePassed_AgedBrie_Quality_30_31() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Aged Brie", 0, 31) );
		
		// Act
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemBrie = items.get(0);
		assertEquals("Quality should increase by one even sell-by date passed", 31, itemBrie.getQuality());
	}
	
	@Test
	public void testUpdateEndOfDay_NoQualityDecrease_Sulfuras_Quality_80_80() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
		
		// Act
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemSulfuras = items.get(0);
		assertEquals("Quality of Sulfuras should stay the same", 80, itemSulfuras.getQuality());
	}
	
	@Test
	public void testUpdateEndOfDay_NoSellInDateDecrease_Sulfuras_SellIn_2_2() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Sulfuras, Hand of Ragnaros", 2, 80));
		
		// Act
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemSulfuras = items.get(0);
		assertEquals("SellIn of Sulfuras should not decrease", 2, itemSulfuras.getSellIn());
	}
	
	@Test
	public void testUpdateEndOfDay_BackStagePassQualityIncreaseBy2SellBy9_Quality_10_12() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 10, 10));
		
		// Act
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemBastagePass = items.get(0);
		assertEquals("Backstage Pass Quality should increase by 2 when there are 10 days or less", 12, itemBastagePass.getQuality());
	}
	
	@Test
	public void testUpdateEndOfDay_BackStagePassQualityIncreaseBy2SellBy4_Quality_10_13() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 5, 10));
		
		// Act
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemBackstagePass = items.get(0);
		assertEquals("Backstage Pass Quality should increase by 3 when there are 5 days or less", 13, itemBackstagePass.getQuality());
	}
	
	@Test
	public void testUpdateEndOfDay_BackstageQualityDropsTo0_Quality_10_0() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 0, 10));
		
		// Act
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemBackstagePass = items.get(0);
		assertEquals("Backstage Pass Quality should drop to 0 after the concert", 0, itemBackstagePass.getQuality());
	}
	
	
	
	
	
}
