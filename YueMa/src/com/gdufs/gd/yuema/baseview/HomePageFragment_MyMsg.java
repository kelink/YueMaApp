package com.gdufs.gd.yuema.baseview;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.gdufs.gd.yuema.baseview.PullToRefreshBase.OnRefreshListener;
import com.gdufs.yuema.R;

/**
 * 主页中我的动态Fragment
 * 
 * @author Administrator
 * 
 */
public class HomePageFragment_MyMsg extends Fragment {

	private ListView mListView;
	private PullToRefreshListView mPullListView;
	private ArrayAdapter<String> mAdapter;
	private LinkedList<String> mListItems;
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
	private boolean mIsStart = true;
	private int mCurIndex = 0;
	private static final int mLoadDataCount = 100;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home_mine, null);
		mPullListView = (PullToRefreshListView) view
				.findViewById(R.id.home_mine_list);
		// 设置属性
		// 设置加载和滚动
		mPullListView.setPullLoadEnabled(false);
		mPullListView.setScrollLoadEnabled(true);
		// 填充数据
		mCurIndex = mLoadDataCount;
		mListItems = new LinkedList<String>();
		mListItems.addAll(Arrays.asList(mStrings).subList(0, mCurIndex));
		// 通过adapter设置item样式
		mAdapter = new ArrayAdapter<String>(this.getActivity(),
				android.R.layout.simple_list_item_1, mListItems);
		mListView = mPullListView.getRefreshableView();
		mListView.setAdapter(mAdapter);
		// 设置item点击时间
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				String text = mListItems.get(position) + ", index = "
						+ (position + 1);
				Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
			}
		});

		mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				mIsStart = true;
				new GetDataTask().execute();// 下拉执行更新数据
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				mIsStart = false;
				new GetDataTask().execute();// 加载更多执行更新数据
			}
		});
		setLastUpdateTime();
		mPullListView.doPullRefreshing(true, 500);
		return view;
	}

	// 异步执行加载数据
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
			}
			return mStrings;
		}

		@Override
		protected void onPostExecute(String[] result) {
			boolean hasMoreData = true;
			if (mIsStart) {
				mListItems.addFirst("Added after refresh...");
			} else {
				int start = mCurIndex;
				int end = mCurIndex + mLoadDataCount;
				if (end >= mStrings.length) {
					end = mStrings.length;
					hasMoreData = false;
				}

				for (int i = start; i < end; ++i) {
					mListItems.add(mStrings[i]);
				}

				mCurIndex = end;
			}

			mAdapter.notifyDataSetChanged();
			mPullListView.onPullDownRefreshComplete();
			mPullListView.onPullUpRefreshComplete();
			mPullListView.setHasMoreData(hasMoreData);
			setLastUpdateTime();

			super.onPostExecute(result);
		}
	}

	private void setLastUpdateTime() {
		String text = formatDateTime(System.currentTimeMillis());
		mPullListView.setLastUpdatedLabel(text);
	}

	private String formatDateTime(long time) {
		if (0 == time) {
			return "";
		}

		return mDateFormat.format(new Date(time));
	}

	public static final String[] mStrings = { "Abbaye de Belloc",
			"Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
			"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu",
			"Airag", "Airedale", "Aisy Cendre", "Allgauer Emmentaler",
			"Alverca", "Ambert", "American Cheese", "Ami du Chambertin",
			"Anejo Enchilado", "Anneau du Vic-Bilh", "Anthoriro", "Appenzell",
			"Aragon", "Ardi Gasna", "Ardrahan", "Armenian String",
			"Aromes au Gene de Marc", "Asadero", "Asiago", "Aubisque Pyrenees",
			"Autun", "Avaxtskyr", "Baby Swiss", "Babybel",
			"Baguette Laonnaise", "Bakers", "Baladi", "Balaton", "Bandal",
			"Banon", "Barry's Bay Cheddar", "Basing", "Basket Cheese",
			"Bath Cheese", "Bavarian Bergkase", "Baylough", "Beaufort",
			"Beauvoorde", "Beenleigh Blue", "Beer Cheese", "Bel Paese",
			"Bergader", "Bergere Bleue", "Berkswell", "Beyaz Peynir",
			"Bierkase", "Bishop Kennedy", "Blarney", "Bleu d'Auvergne",
			"Bleu de Gex", "Bleu de Laqueuille", "Bleu de Septmoncel",
			"Bleu Des Causses", "Blue", "Blue Castello", "Blue Rathgore",
			"Blue Vein (Australian)", "Blue Vein Cheeses", "Bocconcini",
			"Bocconcini (Australian)", "Boeren Leidenkaas", "Bonchester",
			"Bosworth", "Bougon", "Boule Du Roves", "Boulette d'Avesnes",
			"Boursault", "Boursin", "Bouyssou", "Bra", "Braudostur",
			"Breakfast Cheese", "Brebis du Lavort", "Brebis du Lochois",
			"Brebis du Puyfaucon", "Bresse Bleu", "Brick", "Brie",
			"Brie de Meaux", "Brie de Melun", "Brillat-Savarin", "Brin",
			"Brin d' Amour", "Brin d'Amour", "Brinza (Burduf Brinza)",
			"Briquette de Brebis", "Briquette du Forez", "Broccio",
			"Broccio Demi-Affine", "Brousse du Rove", "Bruder Basil",
			"Brusselae Kaas (Fromage de Bruxelles)", "Bryndza",
			"Buchette d'Anjou", "Buffalo", "Burgos", "Butte", "Butterkase",
			"Button (Innes)", "Buxton Blue", "Cabecou", "Caboc", "Cabrales",
			"Cachaille", "Caciocavallo", "Caciotta", "Caerphilly",
			"Cairnsmore", "Calenzana", "Cambazola", "Camembert de Normandie",
			"Canadian Cheddar", "Canestrato", "Cantal", "Caprice des Dieux",
			"Capricorn Goat", "Capriole Banon", "Carre de l'Est",
			"Casciotta di Urbino", "Cashel Blue", "Castellano", "Castelleno",
			"Castelmagno", "Castelo Branco", "Castigliano", "Cathelain",
			"Celtic Promise", "Cendre d'Olivet", "Cerney", "Chabichou",
			"Chabichou du Poitou", "Chabis de Gatine", "Chaource", "Charolais",
			"Chaumes", "Cheddar", "Cheddar Clothbound", "Cheshire", "Chevres",
			"Chevrotin des Aravis", "Chontaleno", "Civray",
			"Coeur de Camembert au Calvados", "Coeur de Chevre", "Colby",
			"Cold Pack", "Comte", "Coolea", "Cooleney", "Coquetdale",
			"Corleggy", "Cornish Pepper", "Cotherstone", "Cotija",
			"Cottage Cheese", "Cottage Cheese (Australian)", "Cougar Gold",
			"Coulommiers", "Coverdale", "Crayeux de Roncq", "Cream Cheese",
			"Cream Havarti", "Crema Agria", "Crema Mexicana", "Creme Fraiche",
			"Crescenza", "Croghan", "Crottin de Chavignol",
			"Crottin du Chavignol", "Crowdie", "Crowley", "Cuajada", "Curd",
			"Cure Nantais", "Curworthy", "Cwmtawe Pecorino",
			"Cypress Grove Chevre", "Danablu (Danish Blue)", "Danbo",
			"Danish Fontina", "Daralagjazsky", "Dauphin", "Delice des Fiouves",
			"Denhany Dorset Drum", "Derby", "Dessertnyj Belyj", "Devon Blue",
			"Devon Garland", "Dolcelatte", "Doolin", "Doppelrhamstufel",
			"Dorset Blue Vinney", "Double Gloucester", "Double Worcester",
			"Dreux a la Feuille", "Dry Jack", "Duddleswell", "Dunbarra",
			"Dunlop", "Dunsyre Blue", "Duroblando", "Durrus",
			"Dutch Mimolette (Commissiekaas)", "Edam", "Edelpilz",
			"Emental Grand Cru", "Emlett", "Emmental", "Epoisses de Bourgogne",
			"Esbareich", "Esrom", "Etorki", "Evansdale Farmhouse Brie",
			"Evora De L'Alentejo", "Exmoor Blue", "Explorateur", "Feta",
			"Feta (Australian)", "Figue", "Filetta", "Fin-de-Siecle",
			"Finlandia Swiss", "Finn", "Fiore Sardo", "Fleur du Maquis",
			"Flor de Guia", "Flower Marie", "Folded",
			"Folded cheese with mint", "Fondant de Brebis", "Fontainebleau",
			"Fontal", "Fontina Val d'Aosta", "Formaggio di capra", "Fougerus",
			"Four Herb Gouda", "Fourme d' Ambert", "Fourme de Haute Loire",
			"Fourme de Montbrison", "Fresh Jack", "Fresh Mozzarella",
			"Fresh Ricotta", "Fresh Truffles", "Fribourgeois", "Friesekaas",
			"Friesian", "Friesla", "Frinault", "Fromage a Raclette",
			"Fromage Corse", "Fromage de Montagne de Savoie", "Fromage Frais",
			"Fruit Cream Cheese", "Frying Cheese", "Fynbo", "Gabriel",
			"Galette du Paludier", "Galette Lyonnaise",
			"Galloway Goat's Milk Gems", "Gammelost", "Gaperon a l'Ail",
			"Garrotxa", "Gastanberra", "Geitost", "Gippsland Blue", "Gjetost",
			"Gloucester", "Golden Cross", "Gorgonzola", "Gornyaltajski",
			"Gospel Green", "Gouda", "Goutu", "Gowrie", "Grabetto", "Graddost",
			"Grafton Village Cheddar", "Grana", "Grana Padano", "Grand Vatel",
			"Grataron d' Areches", "Gratte-Paille", "Graviera", "Greuilh",
			"Greve", "Gris de Lille", "Gruyere", "Gubbeen", "Guerbigny",
			"Halloumi", "Halloumy (Australian)", "Haloumi-Style Cheese" };

}
