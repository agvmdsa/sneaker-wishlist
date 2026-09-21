import { createBrowserRouter } from 'react-router-dom';
import { AppLayout } from './app-layout';
import { WishlistListRoute } from './routes/wishlist-list';
import { WishlistItemRoute } from './routes/wishlist-item';
import { SearchRoute } from './routes/search';
import { StatsRoute } from './routes/stats';

export const router = createBrowserRouter([
  {
    element: <AppLayout />,
    children: [
      { path: '/', element: <WishlistListRoute /> },
      { path: '/wishlist/:id', element: <WishlistItemRoute /> },
      { path: '/search', element: <SearchRoute /> },
      { path: '/stats', element: <StatsRoute /> },
    ],
  },
]);
