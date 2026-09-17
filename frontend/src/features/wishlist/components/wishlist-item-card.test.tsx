import { describe, expect, it } from 'vitest';
import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { WishlistItemCard } from './wishlist-item-card';
import type { WishlistItem } from '../types/wishlist-item.schema';

const item: WishlistItem = {
  id: 1,
  externalSneakerId: 'ext-1',
  name: 'Jordan 4 Retro Rare Air',
  brand: 'Jordan',
  imageUrl: null,
  retailPrice: 220,
  size: '10',
  status: 'WANT',
  pricePaid: null,
  notes: null,
  priceDropDetected: false,
  addedAt: '2026-01-01T00:00:00',
  tags: [],
};

function renderCard(overrides: Partial<WishlistItem> = {}) {
  return render(
    <MemoryRouter>
      <WishlistItemCard item={{ ...item, ...overrides }} />
    </MemoryRouter>,
  );
}

describe('WishlistItemCard', () => {
  it('renders the item name, brand, and size', () => {
    renderCard();

    expect(screen.getByText('Jordan 4 Retro Rare Air')).toBeInTheDocument();
    expect(screen.getByText('Jordan · size 10')).toBeInTheDocument();
  });

  it('renders the status as a badge', () => {
    renderCard({ status: 'OWNED' });

    expect(screen.getByText('OWNED')).toBeInTheDocument();
  });

  it('links to the item detail route', () => {
    renderCard({ id: 42 });

    expect(screen.getByRole('link')).toHaveAttribute('href', '/wishlist/42');
  });
});
