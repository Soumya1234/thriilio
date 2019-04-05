package com.udemy.thrillio;

import com.udemy.thrillio.constants.KidFriendlyStatus;
import com.udemy.thrillio.constants.UserType;
import com.udemy.thrillio.controllers.BookmarkController;
import com.udemy.thrillio.entities.Bookmark;
import com.udemy.thrillio.entities.User;

public class View {
	public static void browse(User user, Bookmark[][] bookmarks) {
		System.out.println("\n" + user.getEmail() + " is browsing items ...");
		int bookmarkCount = 0;
		for (Bookmark[] bookmarkRow : bookmarks) {
			for (Bookmark bookmark : bookmarkRow) {
				if (bookmarkCount < DataStore.USER_BOOKMARK_LIMIT) {
					boolean bookmarked = getBookmarkDecision(bookmark);
					if (bookmarked) {
						BookmarkController.getInstance().saveUserBookmark(user, bookmark);
						bookmarkCount++;
						System.out.println("New Item Bookmarked -- " + bookmark);
					}
				}
				// Mark as kid Friendly
				if (user.getUserType().equals(UserType.EDITOR) || user.getUserType().equals(UserType.CHIEF_EDITOR)) {
					if (bookmark.isKidFriendlyEligible()
							&& bookmark.getKidFriendlyStatus().equals(KidFriendlyStatus.UNKNOWN)) {
						String kidFriendlyMarkDecision = getKidFriendlyMarkDecision(bookmark);
						if(!kidFriendlyMarkDecision.equals(KidFriendlyStatus.UNKNOWN)) {
							bookmark.setKidFriendlyStatus(kidFriendlyMarkDecision);
							System.out.println("Kid-Friendly Status:" + kidFriendlyMarkDecision+" ,"+bookmark);
						}
					}
				}
			}
		}

	}

	private static String getKidFriendlyMarkDecision(Bookmark bookmark) {
		double value = Math.random();
		return value < 0.4 ? KidFriendlyStatus.APPROVED
				: (value >= 0.4 && value < 0.8 ? KidFriendlyStatus.REJECTED : KidFriendlyStatus.UNKNOWN);

	}

	private static boolean getBookmarkDecision(Bookmark bookmark) {
		return Math.random() < 0.5 ? true : false;
	}

//	  public static void bookmark(User user, Bookmark[][] bookmarks) {
//		System.out.println("\n" + user.getEmail() + " is bookmarking ...");
//		for (int i = 0; i < DataStore.USER_BOOKMARK_LIMIT; i++) {
//			int typeOffset = (int) (Math.random() * DataStore.BOOKMARK_TYPES_COUNT);
//			int bookmarkOffset = (int) (Math.random() * DataStore.BOOKMARK_COUNT_PER_TYPE);
//
//			Bookmark bookmark = bookmarks[typeOffset][bookmarkOffset];
//			BookmarkController.getInstance().saveUserBookmark(user, bookmark);
//
//			System.out.println(bookmark);
//		}
//	}

}
